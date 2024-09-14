package com.BRS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BRS.entity.Client;
import com.BRS.service.ClientService;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/getClients")
    public ResponseEntity<List<Client>> getClinets() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/getClient/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {

        try {
            if (clientService.getClient(id) != null)
                return ResponseEntity.ok(clientService.getClient(id));
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

    }

    @PostMapping("/saveClient")
    public ResponseEntity<String> saveClient(@RequestBody Client client) {
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

    @PutMapping("/updateClient")
    public ResponseEntity<String> updateClient(@RequestBody Client client) {
        try {
            if (clientService.updateClient(client) != null) {
                return new ResponseEntity<>("Client information successfully updated!", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Failed", HttpStatus.NOT_ACCEPTABLE);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Failed" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @DeleteMapping("/deleteClient/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        if (clientService.deleteClient(id)) {
            return new ResponseEntity<>("Client removed successfully!", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Unable to remove the client!", HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
