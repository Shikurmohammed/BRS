package com.BRS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.BRS.entity.Book;
import com.BRS.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
@RestController: special type of @Controller, 
               : unlike @Controller(which generates html view) it generates json/xml instead of html view   
               : it will make a class RESTFull
 */
@RestController
/*
 * @RequestMapping: specifies the base url for all endpoints in this class
 */
@RequestMapping("/api")
/*
 * This class contains methods to create, update, fetch, search & delete book
 * details
 */
public class BookController {
    /*
     * @Autowired: used to inject a spring boot beans,( classes decorated
     * with @Component, @Service, @Repository,& @Controller) without explicitly
     * instantiating their objects
     * Here , we are
     */
    @Autowired
    private BookService bookService;

    private final ObjectMapper objectMapper = new ObjectMapper();// jackson object mapper: deserializes json to object
                                                                 // like Book object

    @GetMapping("/getBooks")
    public ResponseEntity<List<Book>> getBookList() {
        List<Book> books = bookService.getBookList();
        return new ResponseEntity<>(books, HttpStatus.OK);// don't use HttpStatus.Found here, since it redirects the url
        // to other resources, eg. An unexpected error occurred. Please try again later.
        // when i try to navigate to will occur
    }

    @GetMapping("/getBook/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        // System.out.println(bookService.getBook(id));
        return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);

    }

    @PostMapping(value = "/saveBook")
    public ResponseEntity<Book> saveBook(@RequestPart("book") String bookJson, // fetch's the book from the request as a
                                                                               // json string
            @RequestPart("image") MultipartFile image) {
        try {
            Book book = objectMapper.readValue(bookJson, Book.class);
            Book savedBook = bookService.saveBook(book, image);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (MultipartException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>("The Book is removed successfully", HttpStatus.OK);

    }

    @GetMapping("/searchBook")
    public ResponseEntity<Book> searchBook() {
        return null;
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@RequestBody Book book, @PathVariable Long id) {
        System.out.println("update with" + id);
        return new ResponseEntity<>(bookService.updateBook(book, id), HttpStatus.OK);

    }

}
