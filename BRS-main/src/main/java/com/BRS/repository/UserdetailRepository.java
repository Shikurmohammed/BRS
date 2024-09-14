package com.BRS.repository;

import com.BRS.entity.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserdetailRepository implements UserRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public UserModel findByusername(String user) {
        String SQL="SELECT * FROM users where username=?";
        return jdbcTemplate.queryForObject(SQL,new Object[]{user},new BeanPropertyRowMapper<>(UserModel.class));

    }
}
