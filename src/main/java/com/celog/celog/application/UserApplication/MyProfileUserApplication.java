package com.celog.celog.application.UserApplication;

import com.celog.celog.controller.boardDto.userDto.userResponseDto.MyProfileUserResponseDto;
import com.celog.celog.repository.UserRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyProfileUserApplication {
    private final UserRepository userRepository;

    @Transactional
    public MyProfileUserResponseDto execute(String email) {
        return userRepository.findByEmail(email)
                .map(user -> MyProfileUserResponseDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .age(user.getAge())
                        .build())
                .orElseThrow(() -> new HttpExceptionCustom(
                        false,
                        "해당 유저가 존재하지 않습니다.",
                        HttpStatus.NOT_FOUND
                ));
    }
}
