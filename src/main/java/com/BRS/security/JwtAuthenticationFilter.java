package com.BRS.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.BRS.service.ClientService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ClientService clientService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/login") ||
                requestURI.equals("/api/authenticate") ||
                requestURI.equals("/home")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            final String autheader = request.getHeader("Authorization");
            if (autheader == null && !autheader.startsWith("Beare")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing or invlaid");
            }
            if (autheader != null && !autheader.startsWith("Beare")) {
                filterChain.doFilter(request, response);
            }
            if (autheader != null) {
                String jwt = autheader.substring(7);
                String username = jwtUtil.extractUsername(jwt);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = clientService.loadUserByUsername(username);
                    if (userDetails != null && jwtUtil.isTokenValid(jwt)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                username, userDetails.getPassword(), userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }

                }
                doFilter(request, response, filterChain);
            }
        } catch (Exception e) {
            System.out.println("Filtering issue:-" + e.getMessage());

        }

    }

}
