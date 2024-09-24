package com.BRS.entity;

import lombok.Data;

@Data
public class UserModel {
    Integer user_id;
    String username;
    String password;
    String phone;
    String role;
}
