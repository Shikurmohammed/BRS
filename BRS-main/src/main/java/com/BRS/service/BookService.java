package com.BRS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BRS.entity.Book;
import com.BRS.mapper.BookMapper;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    public List<Book> getBookList() {

        return bookMapper.getBookList();
    }

    public Optional<Book> getBook(Long id) {

        return bookMapper.getBook(id);
    }

    public Book saveBook(Book book) throws Exception {
        bookMapper.saveBook(book);
        return book;
    }

    public void deleteBook(Long id) {
        bookMapper.deleteBook(id);
    }

    public Book searchBook() {
        return null;
    }

    public Book updateBook(Book book) {
        try {
            Book abook = bookMapper.getBook(book.getId()).get();
            if (abook != null) {
                bookMapper.updateBook(book);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return book;
    }

}
