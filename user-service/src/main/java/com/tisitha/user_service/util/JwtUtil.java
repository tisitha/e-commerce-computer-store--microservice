package com.tisitha.user_service.util;

import com.tisitha.user_service.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret){
        byte[] keyBytes = Base64.getDecoder()
                .decode(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email,String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(secretKey)
                .compact();
    }

    public void validateToken(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e){
            throw new JwtException("JWT validation failed: " + e.getMessage());
        }
    }

    public void validateAdminToken(String token) {
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            if(!claims.get("role").equals(UserRole.ROLE_ADMIN)){
                throw new JwtException("Not an admin");
            }
        } catch (JwtException e){
            throw new JwtException("JWT validation failed: " + e.getMessage());
        }
    }

    public void validateTokenSubject(String token, String expectedEmail) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            if (!claims.getSubject().equals(expectedEmail)) {
                throw new JwtException("Token does not match with user");
            }
        } catch (JwtException e) {
            throw new JwtException("JWT validation failed: " + e.getMessage());
        }
    }
}
