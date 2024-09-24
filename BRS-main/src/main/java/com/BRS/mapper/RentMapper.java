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

import com.BRS.entity.Rent;

@Mapper
public interface RentMapper {
    @Select("Select * from Rent")
    List<Rent> getRentList();

    @Select("Select * from Rent where Id=#{id}")
    Optional<Rent> getRent(@Param("id") Long id);

    @Select("Select  * from Rent where Id=#{id}")
    List<Rent> searchRentByKey(@Param("Key") Rent Key);

    @Insert("INSERT into Rent (BookId,ClientId,RentDate,DueDate,ReturnDate,RentAmount,Status)" +
            "values(#{bookId},#{clientId},#{rentDate},#{dueDate},#{returnDate},#{rentAmount},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveRent(Rent rent);

    @Delete("Delete from Rent where Id =#{id}")
    void removeRent(@Param("id") Long id);

    // @Update("Update Rent set
    // BookId=#{bookId},ClientId=#{clientId},RentDate=#{rentDate}," +
    // "DueDate=#{dueDate},ReturnedDate=#{returnedDate},RentAmount=#{rentAmount},Status=#{status},"
    // +
    // "Penalty=#{penalty} where Id=#{id}")
    // int updateRent(Rent rent);

    @Update("Update Rent set ReturnDate=#{returnDate},Status=#{status} , Penalty=#{penalty} where Id=#{id}")
    int returnBook(Rent rent);

}
