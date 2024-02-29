package com.celog.celog.controller;


import com.celog.celog.application.UserApplication.LoginUserApplication;
import com.celog.celog.application.UserApplication.SignupUserApplication;
import com.celog.celog.controller.dto.userDto.userRequestDto.LoginRequestDto;
import com.celog.celog.controller.dto.userDto.userRequestDto.SignupRequestDto;
import com.celog.celog.controller.dto.userDto.userResponseDto.SignupResponseDto;
import com.celog.celog.shared.CoreSuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@Tag(name = "user", description = "유저 API")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final SignupUserApplication signupUserApplication;
    private final LoginUserApplication loginUserApplication;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public CoreSuccessResponse signup(
            @RequestBody @Valid SignupRequestDto request
    ) {
        SignupResponseDto signupUserResponseDto = signupUserApplication.execute(request);
        return CoreSuccessResponse.builder()
                .ok(true)
                .message("회원가입 성공")
                .httpStatus(HttpStatus.CREATED.value())
                .data(signupUserResponseDto)
                .build();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public CoreSuccessResponse login(
            @RequestBody @Valid LoginRequestDto request
    ) {
        LoginRequestDto loginRequestDto = loginUserApplication.execute(request);
    }
}
