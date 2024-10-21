package com.BRS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BRS.entity.Roles;
import com.BRS.entity.Users;
import com.BRS.exception.DuplicateKeyException;
import com.BRS.exception.NotFoundException;
import com.BRS.mapper.RoleMapper;
import com.BRS.mapper.UserMapper;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    public Users saveUser(Users user) {
        try {
            userMapper.saveUser(user);
            roleMapper.saveUserRole(user.getId());
            return user;
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateKeyException(ex.getMessage());
        }
    }

    public List<Users> getAllUsers() {
        return userMapper.getAllUsers();
    }

    public String updateUser(Users user) {
        try {
            int affectedRows = userMapper.updateUser(user);
            if (affectedRows <= 0) {
                throw new NotFoundException("Unable to Update User information!");
            }
            return "User updated successfully!";
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal error :" + e.getMessage());
        }
    }

    public String deleteUser(Long id) {
        try {
            int affectedRows = userMapper.deleteUser(id);
            if (affectedRows == 0) {
                throw new NotFoundException("The User with ID " + id + " is not found Yet!");
            }
            return "User deleted successfully!";
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error occurred:" + ex.getMessage(), ex);
        }
    }

    public Users getUser(Integer id) {

        Users user = userMapper.getUser(id);

        if (user == null) {
            throw new NotFoundException("Book with ID " + id + " Not Found!");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userMapper.findByUsername(username);

        if (user != null) {
            List<GrantedAuthority> authorities = getAuthoritiesByUserId(user.getId());
            System.out.println(authorities);
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .build();

        } else {
            throw new UsernameNotFoundException("A user with username " + username + "not found!");
        }
    }

    public List<GrantedAuthority> getAuthoritiesByUserId(Long userId) {
        try {
            Set<Roles> roles = roleMapper.findRolesByUserId(userId);
            Set<GrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName().trim()))
                    .collect(Collectors.toSet());
            return new ArrayList<>(authorities);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
