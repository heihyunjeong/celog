package com.celog.celog.application.UserApplication;

import com.celog.celog.controller.dto.userDto.userRequestDto.LoginRequestDto;
import com.celog.celog.controller.dto.userDto.userResponseDto.LoginResponseDto;
import com.celog.celog.domain.User;
import com.celog.celog.repository.UserRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserApplication {
    private final UserRepository userRepository;

    private String passwordCompare(String password, String hashedPassword) {
        if(!BCrypt.checkpw(password, hashedPassword)) {
            throw new HttpExceptionCustom(
                    false,
                    "비밀번호가 일치하지 않습니다.",
                    HttpStatus.BAD_REQUEST
            );
        }

        return hashedPassword;
    }

    private String createAccessToken(Long id) {
        return
    }

    public LoginResponseDto execute(LoginRequestDto request) {
        String email = request.getEmail();

        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new HttpExceptionCustom(
                                false,
                                "가입되지 않은 이메일입니다.",
                                HttpStatus.BAD_REQUEST
                        )
                );

        String checkedPassword = passwordCompare(
                request.getPassword(),
                foundUser.getPassword()
        );

        return LoginResponseDto.builder()
                .email(foundUser.getEmail())
                .build();
    }
}
