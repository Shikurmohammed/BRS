package com.BRS.mapper;

import java.util.List;

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

    @Select("Select * from tbl_Book")
    List<Book> getBookList();

    @Select("Select  * from tbl_Book where id=#{id}")
    Book getBook(@Param("id") Long id);

    @Select("Select * from tbl_Book where id=#{id}")
    List<Book> searchBookByKey(@Param("id") Long id);

    @Insert("INSERT INTO tbl_Book (Title,Author,ISBN,PublicationDate,Genre,Type,RegistrationDate,NumberOfCopies,Edition) VALUES(#{title},#{author},#{iSBN},#{publicationDate},#{genre},#{type},#{registrationDate},#{numberOfCopies},#{edition})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveBook(Book book);

    @Delete("Delete From tbl_Book where Id =#{id}")
    int deleteBook(@Param("id") Long id);

    @Update("Update tbl_Book set Title=#{ Book.title},Author=#{ Book.author},ISBN=#{ Book.iSBN},PublicationDate=#{ Book.publicationDate},Genre=#{ Book.genre},Type=#{ Book.type},"
            +
            "RegistrationDate=#{ Book.registrationDate},NumberOfCopies=#{ Book.numberOfCopies} ,Edition=#{ Book.edition}  where Id=#{currentId}")
    int updateBook(@Param("Book") Book book, @Param("currentId") Long currentId);
}
