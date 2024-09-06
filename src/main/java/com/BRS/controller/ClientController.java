package com.BRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BRS.entity.Client;
import com.BRS.service.ClientService;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/saveClient")
    public ResponseEntity<String> saveClient(@RequestBody Client client) {
        System.out.println();
        try {
            if (clientService.saveClient(client) != null) {
                return new ResponseEntity<>("Client Registered Successfuly:", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error" + e.getMessage(), HttpStatus.FORBIDDEN);
        }

    }

}
