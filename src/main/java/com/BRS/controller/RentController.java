package com.BRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BRS.entity.Rent;
import com.BRS.service.RentService;

@RestController
@RequestMapping("/api")
public class RentController {
    @Autowired
    private RentService rentService;

    @PostMapping("/rentBook")
    public ResponseEntity<String> rentBook(@RequestBody Rent rent) {
        // System.out.println("Rent Details" + rent);
        try {
            boolean savedRent = rentService.rentBook(rent);
            if (savedRent)
                return new ResponseEntity<>("Book Rent detail has saved successfully:", HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Failed: ", HttpStatus.NOT_ACCEPTABLE);

        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/returnBook/{id}")
    public ResponseEntity<String> returnBook(@PathVariable Long id) {
        try {
            if (rentService.returnBook(id))
                return new ResponseEntity<>("Book is Returned!", HttpStatus.ACCEPTED);
            else
                return new ResponseEntity<>("Failed", HttpStatus.NOT_MODIFIED);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Error", HttpStatus.NOT_MODIFIED);
        }

    }
}
