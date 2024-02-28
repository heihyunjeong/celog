package com.celog.celog.service;

import com.celog.celog.application.UserApplication.SignupUserApplication;
import com.celog.celog.controller.dto.userDto.userRequestDto.SignupRequestDto;
import com.celog.celog.domain.User;
import com.celog.celog.repository.UserRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignupUserApplicationTest {
    @Mock // 테스트 대상이 의존하는 객체
    private UserRepository userRepository;
    @InjectMocks // 테스트 대상
    private SignupUserApplication signUpUserApplication;

    private User user;
    private String email;
    private String password;

    @BeforeEach
    public void setUp() {
        email = "ash@gmail.com";
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
    @DisplayName("회원가입 존재하는 이메일 입니다.")
    public void existUserSignUpTest() {
        // given

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        // HTTP 응답 모의 객체 생성
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .email(email)
                .password(hashedPassword)
                .age(20L)
                .build();

        // when
        // UserRepository의 모의 동작 설정
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));

        // then
        HttpExceptionCustom exception = Assertions.assertThrows(HttpExceptionCustom.class, () -> {
            signUpUserApplication.execute(signupRequestDto);
        });

        Assertions.assertEquals("이미 가입된 이메일 입니다.", exception.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
}
