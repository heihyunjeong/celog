package com.celog.celog.service;

import com.celog.celog.application.UserApplication.LoginUserApplication;
import com.celog.celog.domain.User;
import com.celog.celog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

@ExtendWith(MockitoExtension.class)
public class LoginUserApplicationTest {
    @Mock // 테스트 대상이 의존하는 객체
    private UserRepository userRepository;
    @InjectMocks // 테스트 대상
    private LoginUserApplication loginUserApplication;

    private User user;
    private String email;
    private String password;

    @BeforeEach
    public void setUp() {
        email = "robert@gmail.com";
        password = "dfoi23oih123";

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // User 객체 생성
        user = User.builder()
                .id(1L)
                .email(email)
                .age(20L)
                .password(hashedPassword)
                .build();
    }

    @Test
    @DisplayName("존재하지 않는 이메일입니다.")
    public void notExistUserLoginTest() {
        // given
        String doesNotEmail = "slkjdfhklasjdhf@gmail.com";

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // User 객체 생성
        user = User.builder()
                .id(1L)
                .email(email)
                .age(20L)
                .password(hashedPassword)
                .build();

        // when


        // then



    }
}
