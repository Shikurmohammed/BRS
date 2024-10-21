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
import org.springframework.web.bind.annotation.RestController;

import com.BRS.entity.Users;
import com.BRS.service.UsersService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UsersService userService;

    @GetMapping("/getUsers")
    public ResponseEntity<List<Users>> getClients() {
        List<Users> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<Users> getUser(@PathVariable Integer id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody Users User) {
        Users user = userService.saveUser(User);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody Users User) {
        userService.updateUser(User);
        return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User removed successfully!", HttpStatus.OK);
    }

}
