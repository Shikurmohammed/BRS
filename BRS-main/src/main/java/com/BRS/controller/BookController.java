package com.BRS.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BRS.entity.Book;
import com.BRS.service.BookService;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/getBooks")
    public ResponseEntity<List<Book>> getBookList() {
        List<Book> books = bookService.getBookList();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/getBook/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Optional<Book> abook = bookService.getBook(id);

        try {
            if (abook.isPresent()) {
                return ResponseEntity.ok(abook.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return null;
        }

    }

    @PostMapping("/saveBook")
    public ResponseEntity<String> saveBook(@RequestBody Book book) {

        try {
            Book savedbook = bookService.saveBook(book);
            if (savedbook != null) {
                return new ResponseEntity<>("Book saved Successfully with Book ID:" + book.getId(),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Fail:", HttpStatus.NOT_ACCEPTABLE);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Error:" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            if (bookService.getBook(id) != null) {
                bookService.deleteBook(id);
                return new ResponseEntity<>("The Book is removed successfully", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("The Book with ID " + id + " is not found Yet!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/searchBook")
    public ResponseEntity<Book> searchBook() {
        return null;
    }

    @PutMapping("/updateBook")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        ;
        return ResponseEntity.ok(bookService.updateBook(book));

    }
}
