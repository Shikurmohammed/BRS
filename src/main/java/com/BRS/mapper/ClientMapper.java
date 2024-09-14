package com.BRS.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.BRS.entity.Client;

@Mapper
public interface ClientMapper {

    @Insert("Insert into Client(FirstName,LastName,Username,Password,Email,PhoneNumber,RegistrationDate,Address,KebeleId) values(#{firstName},#{lastName},#{username},#{password},#{email},#{phoneNumber},#{registrationDate},#{address},#{kebeleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveClient(Client client);

    @Select("SELECT * FROM Client")
    List<Client> getAllClients();

    @Select("SELECT * FROM Client where Id =#{id}")
    Optional<Client> getClient(@Param("id") Long id);

    @Select("SELECT * FROM Client where Username=#{username}")
    Client findByUsername(String username);

    @Update("UPDATE Client SET FirstName=#{firstName},LastName=#{lastName},Username=#{username}," +
            "Password=#{password},Email=#{email},PhoneNumber=#{phoneNumber},RegistrationDate=#{registrationDate}," +
            "Address=#{address},KebeleId=#{kebeleId} where Id=#{id}")
    int updateClient(Client client);

    @Delete("Delete from Client where Id =#{id}")
    void deleteClient(@Param("id") Long id);
}
