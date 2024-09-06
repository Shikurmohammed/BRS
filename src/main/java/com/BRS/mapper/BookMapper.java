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

import com.BRS.entity.Book;

@Mapper
public interface BookMapper {

    @Select("Select * from Book")
    List<Book> getBookList();

    @Select("Select  * from Book where id=#{id}")
    Optional<Book> getBook(@Param("id") Long id);

    @Select("Select * from Book where id=#{id}")
    List<Book> searchBookByKey(@Param("id") Long id);

    @Select("Select * from Book where Available=1")
    List<Book> getAvailableBooks();

    @Insert("INSERT INTO Book (Title,Author,ISBN,PublicationDate,Genre,Type,RegistrationDate,NumberOfCopies,Edition,Available) VALUES(#{title},#{author},#{iSBN},#{publicationDate},#{genre},#{type},#{registrationDate},#{numberOfCopies},#{edition},#{available})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveBook(Book book);

    @Delete("Delete From Book where Id =#{id}")
    void deleteBook(@Param("id") Long id);

    @Update("Update Book set Title=#{title},Author=#{author},ISBN=#{iSBN},PublicationDate=#{publicationDate},Genre=#{genre},Type=#{type},RegistrationDate=#{registrationDate} NumberOfCopies=#{numberOfCopies} Edition=#{Edition} Available=#{available} where Id=#{id}")
    int updateBook(Book book);

}
