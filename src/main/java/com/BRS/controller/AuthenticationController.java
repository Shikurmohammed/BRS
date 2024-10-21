package com.BRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BRS.email.EmailService;
import com.BRS.entity.AuthResponse;
import com.BRS.entity.LoginForm;
import com.BRS.security.JwtUtil;
import com.BRS.service.UsersService;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @Autowired
    private UsersService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody LoginForm loginForm) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

            UserDetails currentLoggedInUser = userService.loadUserByUsername(loginForm.getUsername());
            System.out.println(currentLoggedInUser.getAuthorities().toString());
            if (authentication.isAuthenticated()) {
                AuthResponse authResponse = new AuthResponse(
                        jwtUtil.generateToken(currentLoggedInUser),
                        loginForm.getUsername(), currentLoggedInUser.getAuthorities().toString());
                // emailService.sendEmail("", "", "Greetings", "Hello everyBody");
                // emailService.sendMail();
                return new ResponseEntity<>(authResponse, HttpStatus.OK);
            } else {
                throw new UsernameNotFoundException(loginForm.getUsername());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
