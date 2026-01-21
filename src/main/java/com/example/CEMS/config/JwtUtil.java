package com.example.CEMS.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET =
            "cems-super-secret-key-which-is-32-bytes-long";

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    // ✅ Generate JWT
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extract username
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ Extract role
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // ✅ Centralized claims parser
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Strong validation
    public boolean validateToken(String token, String username) {
        try {
            Claims claims = getClaims(token);

            String tokenUsername = claims.getSubject();
            Date expiration = claims.getExpiration();

            return tokenUsername.equals(username)
                    && expiration.after(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
