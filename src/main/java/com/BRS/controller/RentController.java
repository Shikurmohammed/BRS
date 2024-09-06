package com.BRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/saveRent")
    public ResponseEntity<String> saveRent(@RequestBody Rent rent) {
        System.out.println("Rent Details" + rent);
        try {
            Rent savedRent = rentService.saveRent(rent);
            if (savedRent != null)
                return new ResponseEntity<>("Book Rent detail has saved successfully:", HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Failed: ", HttpStatus.NOT_ACCEPTABLE);

        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
