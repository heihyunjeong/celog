package com.celog.celog.controller;


import com.celog.celog.application.UserApplication.LoginUserApplication;
import com.celog.celog.application.UserApplication.MyProfileUserApplication;
import com.celog.celog.application.UserApplication.SignupUserApplication;
import com.celog.celog.application.UserApplication.UpdateMyProfileApplication;
import com.celog.celog.controller.dto.userDto.userRequestDto.LoginRequestDto;
import com.celog.celog.controller.dto.userDto.userRequestDto.SignupRequestDto;
import com.celog.celog.controller.dto.userDto.userRequestDto.UpdateProfileRequestDto;
import com.celog.celog.controller.dto.userDto.userResponseDto.LoginResponseDto;
import com.celog.celog.controller.dto.userDto.userResponseDto.MyProfileUserResponseDto;
import com.celog.celog.controller.dto.userDto.userResponseDto.SignupResponseDto;
import com.celog.celog.controller.dto.userDto.userResponseDto.UpdateProfileResponseDto;
import com.celog.celog.domain.User;
import com.celog.celog.shared.CoreSuccessResponse;
import com.celog.celog.shared.service.SecurityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "user", description = "유저 API")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final SignupUserApplication signupUserApplication;
    private final LoginUserApplication loginUserApplication;
    private final MyProfileUserApplication myProfileUserApplication;
    private final UpdateMyProfileApplication updateMyProfileApplication;
    private final SecurityService securityService;

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
        LoginResponseDto loginResponseDto = loginUserApplication.execute(request);
        return CoreSuccessResponse.builder()
                .ok(true)
                .message("로그인 성공")
                .httpStatus(HttpStatus.OK.value())
                .data(loginResponseDto)
                .build();
    }

    @GetMapping("/my_profile")
    @ResponseStatus(HttpStatus.OK)
    public CoreSuccessResponse myProfile(
            @RequestHeader("Authorization") String token
    ) {
        User responseUser = securityService.getSubject(token);
        MyProfileUserResponseDto myProfileUserResponseDto = myProfileUserApplication.execute(responseUser.getEmail());
        return CoreSuccessResponse.builder()
                .ok(true)
                .message("내 프로필 조회 성공")
                .data(myProfileUserResponseDto)
                .httpStatus(HttpStatus.OK.value())
                .build();
    }

    @PutMapping("/my_profile")
    @ResponseStatus(HttpStatus.OK)
    public CoreSuccessResponse updateMyProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid UpdateProfileRequestDto request
    ) {
        User responseUser = securityService.getSubject(token);
        UpdateProfileResponseDto updateProfileResponseDto = updateMyProfileApplication.execute(responseUser, request);
        return CoreSuccessResponse.builder()
                .ok(true)
                .message("내 프로필 수정 성공")
                .data(updateProfileResponseDto)
                .httpStatus(HttpStatus.OK.value())
                .build();
    }
}
