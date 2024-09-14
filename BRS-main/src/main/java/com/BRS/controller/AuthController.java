package com.BRS.controller;

import com.BRS.dto.AuthRequestDto;
import com.BRS.dto.AuthResponseDto;
import com.BRS.entity.UserModel;
import com.BRS.repository.UserRepository;
import com.BRS.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;//to put get or put username and password we have to inject Authentication Manager
    @Autowired
    private UserDetailsService userDetailsService;// to get user name and password from database
    @Autowired
    JwtUtilService jwtUtilService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto) {
        try {
            BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
            String get="getu";
            String enc=bCryptPasswordEncoder.encode(get);
            System.out.println("Encoded password was"+enc);

            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUser(), authRequestDto.getPassword()));
            //validate user details with the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequestDto.getUser());
            //usermodel to get user
            UserModel userModel = userRepository.findByusername(authRequestDto.getUser());
            //last one is generate token
            String jwtToken = this.jwtUtilService.generateToken(userDetails,userModel.getRole());
            String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, userModel.getRole());
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwtToken);
            authResponseDto.setRefreshToken(refreshToken);

            return new ResponseEntity<AuthResponseDto>(authResponseDto, HttpStatus.OK);




           // return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (Exception e) {
 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("error Authentications"+e.getMessage());
        }
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> auth(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        try {
            String username = jwtUtilService.extractUsername(refreshToken);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            UserModel userModel = userRepository.findByusername(username);

            if(jwtUtilService.validateToken(refreshToken, userDetails)) {
                String newJwt = jwtUtilService.generateToken(userDetails, userModel.getRole());
                String newRefreshToken = jwtUtilService.generateRefreshToken(userDetails, userModel.getRole());

                AuthResponseDto authResponseDto = new AuthResponseDto();
                authResponseDto.setToken(newJwt);
                authResponseDto.setRefreshToken(newRefreshToken);

                return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
            }


        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error refresh token:::" + e.getMessage());
        }

    }




}
