package com.BRS.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client implements UserDetails {
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
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
