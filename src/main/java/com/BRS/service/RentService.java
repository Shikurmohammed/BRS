package com.BRS.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BRS.entity.Book;
import com.BRS.entity.Rent;
import com.BRS.mapper.BookMapper;
import com.BRS.mapper.RentMapper;

@Service
public class RentService {
    @Autowired
    RentMapper rentMapper;

    @Autowired
    private BookMapper bookMapper;
    private final static double DAILY_PENALITY_RATE = 2.0;

    public Boolean rentBook(Rent rent) {
        boolean flag = false;
        try {
            Optional<Book> bookOptional = bookMapper.getBook(rent.getBookId());
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                if (book.getNumberOfCopies() > 0) {
                    Rent addRent = new Rent();
                    addRent.setBookId(rent.getBookId());
                    addRent.setClientId(rent.getClientId());
                    addRent.setRentDate(LocalDateTime.now());
                    addRent.setDueDate(LocalDateTime.now().plusSeconds(5));
                    addRent.setRentAmount(rent.getRentAmount());
                    addRent.setStatus("Rented");
                    addRent.setPenalty(0);
                    // Update book availability
                    book.setNumberOfCopies(book.getNumberOfCopies() - 1);
                    bookMapper.updateBook(book);
                    // Save the rent information
                    rentMapper.saveRent(addRent);

                    flag = true;
                } else {
                    System.out.println("Sorry, the Book is not available for rent!" + rent.getBookId());
                }
            } else {
                System.out.println("Book not found with ID: " + rent.getBookId());
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return flag;
    }

    public Boolean returnBook(Long id) {

        Boolean flag = false;
        try {
            Rent rentedBooks = rentMapper.getRent(id).orElseThrow();

            if (rentedBooks != null && rentedBooks.getReturnDate() == null) {
                Book book = bookMapper.getBook(rentedBooks.getBookId()).orElseThrow();
                if (book != null) {
                    rentedBooks.setReturnDate(LocalDateTime.now());
                    Long daysLate = ChronoUnit.SECONDS.between(rentedBooks.getDueDate(), LocalDateTime.now());

                    double Penalty = daysLate > 0 ? daysLate * DAILY_PENALITY_RATE : 0;
                    rentedBooks.setPenalty(Penalty);
                    rentedBooks.setRentAmount(rentedBooks.getRentAmount() + Penalty);
                    rentedBooks.setStatus("Available");
                    rentMapper.returnBook(rentedBooks);
                    book.setNumberOfCopies(book.getNumberOfCopies() + 1);
                    bookMapper.updateBook(book);

                    flag = true;
                    System.out.println("Success ..." + rentedBooks);
                } else {
                    System.out.println("Failed ...");
                }
            } else {
                System.out.println("Sorry, the book has been returned already!");
                flag = false;
            }
        } catch (Exception e) {
            System.out.println("Error::" + e.getMessage());
            e.printStackTrace();
        }
        return flag;
    }

    public void removeRent(Long id) {
        rentMapper.removeRent(id);
    }

    public List<Rent> searchRent(Rent rent) {
        return rentMapper.searchRentByKey(rent);
    }

}
