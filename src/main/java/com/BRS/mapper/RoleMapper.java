package com.BRS.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.BRS.entity.Roles;

@Mapper
public interface RoleMapper {
    @Select("SELECT * from tbl_Role as r join tbl_User_Role ur on r.Id =ur.RoleId where ur.UserId=#{userId}")
    Set<Roles> findRolesByUserId(Long userId);

    @Insert("Insert into tbl_User_Role(UserId) values(#{userId})")
    int saveUserRole(@Param("userId") Long userId);
}
