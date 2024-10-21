package com.BRS.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BRS.entity.Book;
import com.BRS.entity.Rent;
import com.BRS.exception.NotFoundException;
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
            Book book = bookMapper.getBook(rent.getBookId());
            if (book == null) {
                System.out.println("The Book is not available in the store!");
                throw new NotFoundException("The Book is not available in the store!");
            } else if (book.getNumberOfCopies() == 0) {
                book.setStatus("Rented");
                throw new NotFoundException("Sorry, the Book has been rented out!");
            } else {
                Rent addRent = new Rent();
                addRent.setBookId(rent.getBookId());
                addRent.setClientId(rent.getClientId());
                addRent.setRentDate(LocalDateTime.now());
                addRent.setDueDate(LocalDateTime.now().plusSeconds(5));
                addRent.setRentAmount(rent.getRentAmount());
                addRent.setTotalFee(rent.getRentAmount());
                addRent.setPenalty(0);
                // Update book availability
                book.setNumberOfCopies(book.getNumberOfCopies() - 1);
                bookMapper.updateBook(book, book.getId());
                // Save the rent information
                rentMapper.saveRent(addRent);
                flag = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return flag;
    }

    public Boolean returnBook(Long id) {

        Boolean flag = false;
        try {
            Rent rentedBooks = rentMapper.getRent(id);

            if (rentedBooks != null && rentedBooks.getReturnDate() == null) {
                Book book = bookMapper.getBook(rentedBooks.getBookId());// .orElseThrow();
                if (book != null) {
                    rentedBooks.setReturnDate(LocalDateTime.now());
                    Long daysLate = ChronoUnit.SECONDS.between(rentedBooks.getDueDate(), LocalDateTime.now());

                    double Penalty = daysLate > 0 ? daysLate * DAILY_PENALITY_RATE : 0;
                    rentedBooks.setPenalty(Penalty);
                    rentedBooks.setTotalFee(rentedBooks.getRentAmount() + Penalty);
                    rentMapper.returnBook(rentedBooks);

                    book.setNumberOfCopies(book.getNumberOfCopies() + 1);
                    book.setStatus("Available");
                    bookMapper.updateBook(book, book.getId());

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

    public List<Rent> getAllRent() {
        return rentMapper.getRentList();
    }

}
