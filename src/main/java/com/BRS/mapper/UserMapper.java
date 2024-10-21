package com.BRS.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.BRS.entity.Users;

@Mapper
public interface UserMapper {

    @Insert("Insert into tbl_User(FirstName,LastName,Username,Password,Email,PhoneNumber,RegistrationDate,Address,KebeleId) values(#{firstName},#{lastName},#{username},#{password},#{email},#{phoneNumber},#{registrationDate},#{address},#{kebeleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveUser(Users User);

    @Select("SELECT * FROM tbl_User")
    List<Users> getAllUsers();

    @Select("SELECT * FROM tbl_User where Id =#{id}")
    Users getUser(@Param("id") Integer id);

    @Select("SELECT * FROM tbl_User where Username=#{username}")
    Users findByUsername(String username);

    @Update("UPDATE tbl_User SET FirstName=#{firstName},LastName=#{lastName},Username=#{username}," +
            "Password=#{password},Email=#{email},PhoneNumber=#{phoneNumber},RegistrationDate=#{registrationDate}," +
            "Address=#{address},KebeleId=#{kebeleId} where Id=#{id}")
    int updateUser(Users User);

    @Delete("Delete from tbl_User where Id =#{id}")
    int deleteUser(@Param("id") Long id);
}
