package com.BRS.repository;

import com.BRS.entity.UserModel;

public interface UserRepository {
    public UserModel findByusername(String user);
}
