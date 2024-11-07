package com.example.beskbd.services;

import com.example.beskbd.entities.InvalidatedToken;
import com.example.beskbd.entities.User;
import com.example.beskbd.repositories.InvalidatedTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long expiration;

    public CompletableFuture<String> generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("roles", user.getAuthorities());
        return createToken(claims, user.getUsername());
    }

    public String extractUserName(String token) {
        return extractAllClaims(token)
                .getSubject();
    }

    public boolean isValidJwtToken(String token) {
        try {
            Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public boolean validateToken(String token, UserDetails user) {
        if (invalidatedTokenRepository.existsById(token)) {
            return false;
        }
        Date expirationDate = extractExpiration(token);
        if (expirationDate.before(new Date())) {
            return false;
        }
        String username = extractUserName(token);
        return user.getUsername().equals(username) && expirationDate.after(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token)
                .getExpiration();
    }

    private CompletableFuture<String> createToken(Map<String, Object> claims, String subject) {
        byte[] keyBytes = Base64.getDecoder().decode(this.secretKey);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return CompletableFuture.supplyAsync(() -> Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(key)
                .compact());
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public void invalidateToken(String token) {
        Date expirationDate = extractExpiration(token);
        invalidatedTokenRepository.save(new InvalidatedToken(token, expirationDate));
    }
}
