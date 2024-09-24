package com.BRS.service;

import com.BRS.entity.UserModel;
import com.BRS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel userModel=this.userRepository.findByusername(username);
        if(userModel==null){
            throw  new UsernameNotFoundException(username);
        }
        return new User(userModel.getUsername(),userModel.getPassword(),new ArrayList<>());
    }
}
