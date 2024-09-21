package com.BRS.entity;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter and setter for 'token'
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
