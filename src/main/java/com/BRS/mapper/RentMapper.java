package com.BRS.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.BRS.entity.Rent;

@Mapper
public interface RentMapper {
    @Select("Select * from BookRent")
    List<Rent> getRentList();

    @Select("Select  from BookRent where id=#{id}")
    List<Rent> searchRentByKey(@Param("Key") Rent Key);

    @Insert("Insert into BookRent (BookId,ClientId,RentDate,DueDate,ReturnedDate,RentAmount,Status) values(#{bookId},#{clientId},#{rentDate},#{DueDate},#{returnedDate},#{rentAmount},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Rent saveRent(Rent rent);

    @Delete("Delete from BookRent where Id =#{id}")
    void removeRent(@Param("id") Long id);

    @Update("Update set BookRent")
    Rent updateRent(Rent rent);
}
