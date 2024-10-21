package com.BRS.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.BRS.entity.Book;
import com.BRS.entity.BookImage;
import com.BRS.exception.InvalidDataException;
import com.BRS.exception.NotFoundException;
import com.BRS.mapper.BookImageMapper;
import com.BRS.mapper.BookMapper;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookImageMapper bookImageMapper;

    public List<Book> getBookList() {
        return bookMapper.getBookList();

    }

    public Book getBook(Long id) {
        Book book = bookMapper.getBook(id);
        if (book == null) {
            throw new NotFoundException("Book with ID " + id + " Not Found!");
        }
        return book;
    }

    public Book saveBook(Book book, MultipartFile image) throws IOException {
        try {
            int affectedRows = bookMapper.saveBook(book);
            BookImage bImage = new BookImage();
            if (image != null) {
                bImage.setBookId(book.getId());
                bImage.setImage(image.getBytes());
                bImage.setImageUrl(image.getOriginalFilename());
                bookImageMapper.saveBookImage(bImage);
            }
            if (affectedRows <= 0) {
                throw new InvalidDataException("Something gets wrong with with the data you provided!");
            }
            return book;
        } catch (Exception ex) {
            throw new InvalidDataException(ex.getMessage());
        }
    }

    public String deleteBook(Long id) {
        try {
            int affectedRows = bookMapper.deleteBook(id);
            if (affectedRows == 0) {
                throw new NotFoundException("The Book with ID " + id + " is not found Yet!");
            }
            return "Book deleted successfully!";
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error occurred:" + ex.getMessage(), ex);
        }
    }

    public Book searchBook() {
        return null;
    }

    public String updateBook(Book book, Long id) {
        try {
            int affectedRows = bookMapper.updateBook(book, id);
            if (affectedRows <= 0) {
                throw new NotFoundException("Book with ID " + id + " Not Found!");
            }
            return "Book updated successfully!";
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal error :" + e.getMessage());
        }

    }

}
