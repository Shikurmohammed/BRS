package com.BRS.entity;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {// implements UserDetails
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private LocalDateTime registrationDate;
    private String address;
    private String kebeleId;
    // private String role;
    private Set<Roles> roles;

    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    // return null;
    // }
}
