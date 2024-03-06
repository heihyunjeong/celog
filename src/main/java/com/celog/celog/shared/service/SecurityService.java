package com.celog.celog.shared.service;

import com.celog.celog.domain.User;
import com.celog.celog.repository.UserRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SecurityService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private Long expiration;

    private final UserRepository userRepository;

    public String createToken(Long userId) {

        if (expiration <= 0) {
            throw new RuntimeException("Expiratio time must be greater than zero!");
        }

        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(key)
                .compact();
    }

    public User getSubject(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        String removeBearer = token.replace("Bearer ", "");

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(removeBearer)
                .getBody();
        Long userId = Long.parseLong(claims.getSubject());
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new HttpExceptionCustom(
                                false,
                                "존재하지 않은 사용자입니다.",
                                HttpStatus.NOT_FOUND
                        )
                );
    }

    public String getTokenByCookie(Cookie[] cookies) {
        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }
        return token;
    }
}
