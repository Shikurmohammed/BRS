package com.BRS.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.BRS.service.UsersService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UsersService usersService;

    @Autowired
    public JwtAuthenticationFilter(UsersService usersService, JwtUtil jwtUtil) {
        this.usersService = usersService;
        this.jwtUtil = jwtUtil;
    }

    // jwt token validation will be done here for every request
    // doInternal will take each requests and responses and pass them through the
    // filter chain(3rd Argument) for processing
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String autHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        try {
            if (requestURI.equals("/api/authenticate") ||
                    requestURI.equals("/api/register") ||
                    requestURI.equals("/login") ||
                    requestURI.equals("/home")) {
                filterChain.doFilter(request, response);// allows the filter chain to proceed to the next filter or the
                                                        // dispatcher servlet
                return;
            }

            if (autHeader == null || !autHeader.startsWith("Bearer")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Authorization header is missing or invlaid!");
                return;
            } else {
                String jwt = autHeader.substring(7);
                String username = jwtUtil.extractUsername(jwt);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = usersService.loadUserByUsername(username);
                    if (userDetails != null && jwtUtil.isTokenValid(jwt)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                username, userDetails.getPassword(), userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (ExpiredJwtException eje) {
            // Here i am setting the response directly to the HttpServletResponse and return
            // for each exception's instead of throwing new exception.
            // This is because the doFilter chain is called before any controller or bean
            // files and end up with stack trace issue.
            // And I return immediately to prevent the propagation to other filter chains or
            // controllers with the authentication failure status
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT token has expired!");
            return;

        } catch (MalformedJwtException mje) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token!");
            return;

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT Authentication failed!");
            return;

        }
        doFilter(request, response, filterChain);

    }

}
