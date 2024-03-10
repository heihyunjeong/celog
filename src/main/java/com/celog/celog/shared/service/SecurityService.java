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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
            throw new HttpExceptionCustom(
                    false,
                    "Expiratio time must be greater than zero!",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(key)
                .compact();
    }

    // SecurityService 클래스 내에 추가
    public Authentication getAuthentication(String token) {
        // 토큰에서 사용자 ID를 추출
        Long userId = getUserIdFromToken(token);
        // 데이터베이스에서 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HttpExceptionCustom(false,"User not found: " + userId, HttpStatus.NOT_FOUND));

        // 사용자 권한을 설정 (여기서는 예시로 USER 권한을 부여합니다. 실제로는 사용자의 권한에 맞게 설정해야 합니다.)
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // UsernamePasswordAuthenticationToken 객체 생성 및 반환
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    public User getAuthenticatedUser(String authHeader) {
        if (authHeader == null) {
            throw new HttpExceptionCustom(
                    false,
                    "Authentication credentials not found",
                    HttpStatus.UNAUTHORIZED
            );
        }
        String token = authHeader.substring(7);
        return getSubject(token);
    }

    public Boolean validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        SecretKey
                key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        String removeBearer = token.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(removeBearer)
                .getBody();
        return Long.parseLong(claims.getSubject());
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

//    public String getTokenByCookie(Cookie[] cookies) {
//        String token = null;
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("token")) {
//                System.out.println("여기에 들어오긴하니??");
//                token = cookie.getValue();
//            }
//        }
//        return token;
//    }
}
