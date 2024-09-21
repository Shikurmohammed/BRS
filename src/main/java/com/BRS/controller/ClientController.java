package com.BRS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BRS.entity.AuthResponse;
import com.BRS.entity.Client;
import com.BRS.security.JwtUtil;
import com.BRS.service.ClientService;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody Client ClientForm) {
        try {
            System.out.println("Credentials" + clientService.loadUserByUsername(ClientForm.getUsername()));

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(ClientForm.getUsername(), ClientForm.getPassword()));
            // System.out.println("authe..." + authentication);
            if (authentication.isAuthenticated()) {
                System.out.println(jwtUtil.generateToken(clientService.loadUserByUsername(ClientForm.getUsername())));
                AuthResponse authResponse = new AuthResponse(
                        jwtUtil.generateToken(clientService.loadUserByUsername(ClientForm.getUsername())));

                return ResponseEntity
                        .ok().body(authResponse);
            } else {
                throw new UsernameNotFoundException(ClientForm.getUsername());
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(null);
        }
    }

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
