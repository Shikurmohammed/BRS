package com.BRS.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.BRS.entity.Client;

@Mapper
public interface ClientMapper {

    @Insert("Insert into Client(FirstName,LastName,Username,Password,Email,PhoneNumber,RegistrationDate,Address,KebeleId) values(#{firstName},#{lastName},#{userName},#{password},#{email},#{phoneNumber},#{registrationDate},#{address},#{kebeleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Client saveClient(Client client);
}
