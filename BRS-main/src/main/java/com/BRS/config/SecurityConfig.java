package com.BRS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    //the first program that runs and alters default configurations
    @Bean
    SecurityFilterChain web(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors(withDefaults())
                .csrf(crf ->crf.disable())
                .authorizeHttpRequests((authorize)->authorize.requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                ).sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return httpSecurity.build();
    }
   @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();//we are using provider called authentication manager

    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{

        return authenticationConfiguration.getAuthenticationManager();
    }
}


