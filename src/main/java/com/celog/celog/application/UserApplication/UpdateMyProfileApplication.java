package com.celog.celog.application.UserApplication;

import com.celog.celog.controller.dto.userDto.userRequestDto.UpdateProfileRequestDto;
import com.celog.celog.controller.dto.userDto.userResponseDto.UpdateProfileResponseDto;
import com.celog.celog.domain.User;
import com.celog.celog.repository.UserRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateMyProfileApplication {
    private final UserRepository userRepository;

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

    @Transactional
    public UpdateProfileResponseDto execute(
            User user,
            UpdateProfileRequestDto requestDto
    ) {
        String email = user.getEmail();
        User responseUser = emailCheck(email);

        passwordCompare(requestDto.getPassword(), responseUser.getPassword());
        responseUser.setAge(requestDto.getAge());
        responseUser.setName(requestDto.getName());

        return UpdateProfileResponseDto.builder()
                .email(responseUser.getEmail())
                .name(responseUser.getName())
                .age(responseUser.getAge())
                .build();
    }
}
