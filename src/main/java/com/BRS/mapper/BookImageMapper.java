package com.BRS.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.BRS.entity.BookImage;

@Mapper
public interface BookImageMapper {
    @Select("SELECT * from tbl_book_images")
    Set<BookImage> getBookImageList();

    @Insert("Insert into tbl_book_images(book_id,image,image_url) values(#{bookId},#{image},#{imageUrl})")
    int saveBookImage(BookImage image);
}
