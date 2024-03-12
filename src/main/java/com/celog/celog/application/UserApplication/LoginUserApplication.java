package com.celog.celog.application.UserApplication;

import com.celog.celog.controller.boardDto.userDto.userRequestDto.LoginRequestDto;
import com.celog.celog.controller.boardDto.userDto.userResponseDto.LoginResponseDto;
import com.celog.celog.domain.User;
import com.celog.celog.repository.UserRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import com.celog.celog.shared.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserApplication {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    private void passwordCompare(String password, String hashedPassword) {
        if(!BCrypt.checkpw(password, hashedPassword)) {
            throw new HttpExceptionCustom(
                    false,
                    "비밀번호가 일치하지 않습니다.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private User emailCheck(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new HttpExceptionCustom(
                                false,
                                "가입되지 않은 이메일입니다.",
                                HttpStatus.BAD_REQUEST
                        )
                );
    }

    public LoginResponseDto execute(LoginRequestDto request) {
        String email = request.getEmail();

        User responseUser = emailCheck(email);
        passwordCompare(request.getPassword(), responseUser.getPassword());
        String accessToken = securityService.createToken(responseUser.getId());

        return LoginResponseDto.builder()
                .id(responseUser.getId())
                .email(responseUser.getEmail())
                .accessToken(accessToken)
                .build();
    }
}
