package com.celog.celog.application.UserApplication;

import com.celog.celog.controller.dto.userDto.userRequestDto.SignupRequestDto;
import com.celog.celog.controller.dto.userDto.userResponseDto.SignupResponseDto;
import com.celog.celog.domain.User;
import com.celog.celog.repository.UserRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import com.celog.celog.shared.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupUserApplication {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @Transactional
    public SignupResponseDto execute(
            SignupRequestDto signupRequestDto
    ) {
        userRepository.findByEmail(signupRequestDto.getEmail())
                .ifPresent(user -> {
                    throw new HttpExceptionCustom(
                            false,
                            "이미 가입된 이메일 입니다.",
                            HttpStatus.BAD_REQUEST);
                });
        User createdUser = createUser(signupRequestDto);
        User saved = userRepository.save(createdUser);
        return toSignupResponseDto(saved);
    }


    private User createUser(SignupRequestDto request) {
        return User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()))
                .age(request.getAge())
                .build();
    }

    private SignupResponseDto toSignupResponseDto(User user) {
        return SignupResponseDto.builder()
                .email(user.getEmail())
                .build();
    }
}
