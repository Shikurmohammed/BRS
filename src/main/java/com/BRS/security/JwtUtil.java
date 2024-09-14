package com.BRS.security;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

//This class will generating and validating tokens
@Component
public class JwtUtil {

    @Value("${jwt-secret}")
    private String SECRET_KEY;
    @Value("${jwt-expiration}")
    private Long VALID_FOR;

    /* Start Generating Toke */
    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        claims.put("issuer", "ShikurM");

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALID_FOR)))
                .signWith(generatedKey())
                .compact();

    }

    private SecretKey generatedKey() {
        SECRET_KEY = SECRET_KEY.replace("-", "+");
        SECRET_KEY = SECRET_KEY.replace("_", "/");

        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        SecretKey key = Keys.hmacShaKeyFor(decodedKey);
        return key;

    }

    /* End generating Token */
    /* Start Extracting subject from token claims */
    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    public Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generatedKey())
                .clockSkewSeconds(60)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    /* End extracting subject */
    /* Start validating token */
    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));

    }
    /* End validating token(jwt) */

}
