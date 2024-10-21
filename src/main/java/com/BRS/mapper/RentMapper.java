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
    @Select("Select * from tbl_Rent")
    List<Rent> getRentList();

    @Select("Select * from tbl_Rent where Id=#{id}")
    Rent getRent(@Param("id") Long id);

    @Select("Select  * from tbl_Rent where Id=#{id}")
    List<Rent> searchRentByKey(@Param("Key") Rent Key);

    @Insert("INSERT into tbl_Rent (BookId,ClientId,RentDate,DueDate,ReturnDate,RentAmount,Status)" +
            "values(#{bookId},#{clientId},#{rentDate},#{dueDate},#{returnDate},#{rentAmount},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveRent(Rent rent);

    @Delete("Delete from tbl_Rent where Id =#{id}")
    int removeRent(@Param("id") Long id);

    @Update("Update tbl_Rent set ReturnDate=#{returnDate},Status=#{status} ,TotalFee=#{totalFee}, Penalty=#{penalty} where Id=#{id}")
    int returnBook(Rent rent);

}
