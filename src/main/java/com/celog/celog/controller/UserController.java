package com.celog.celog.controller;

import com.celog.celog.application.UserApplication.LoginUserApplication;
import com.celog.celog.application.UserApplication.SignupUserApplication;
import com.celog.celog.application.UserApplication.UpdateMyProfileApplication;
import com.celog.celog.controller.dto.userDto.userRequestDto.LoginRequestDto;
import com.celog.celog.controller.dto.userDto.userRequestDto.SignupRequestDto;
import com.celog.celog.controller.dto.userDto.userRequestDto.UpdateProfileRequestDto;
import com.celog.celog.controller.dto.userDto.userResponseDto.UpdateProfileResponseDto;
import com.celog.celog.domain.User;
import com.celog.celog.shared.CoreSuccessResponse;
import com.celog.celog.shared.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.celog.celog.shared.CoreSuccessResponse.coreSuccessResponse;

@RestController
@Tag(name = "user", description = "유저 API")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final SignupUserApplication signupUserApplication;
    private final LoginUserApplication loginUserApplication;
    private final UpdateMyProfileApplication updateMyProfileApplication;
    private final SecurityService securityService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "회원가입",
            description = "회원가입"
    )
    public CoreSuccessResponse signup(@RequestBody @Valid SignupRequestDto request) {
        return coreSuccessResponse(signupUserApplication.execute(request), "회원가입 성공", HttpStatus.CREATED.value());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "로그인",
            description = "로그인"
    )
    public CoreSuccessResponse login(@RequestBody @Valid LoginRequestDto request) {
        return coreSuccessResponse(loginUserApplication.execute(request), "로그인 성공", HttpStatus.OK.value());
    }

    @GetMapping("/my_profile")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "내정보 불러오기",
            description = "내정보 불러오기"
    )
    public CoreSuccessResponse getMyProfile(HttpServletRequest httpServletRequest) {
        User foundUser = securityService.getAuthenticatedUser(httpServletRequest);
        User responseUser = createReturnUser(foundUser);

        return coreSuccessResponse(responseUser, "내 프로필 조회 성공", HttpStatus.OK.value());
    }

    @PutMapping("/my_profile")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "내정보 수정하기",
            description = "내정보 수정하기"
    )
    public CoreSuccessResponse updateMyProfile(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid UpdateProfileRequestDto updateProfileRequestDto
    ) {
        User foundUser = securityService.getAuthenticatedUser(httpServletRequest);
        UpdateProfileResponseDto updateProfileResponseDto = updateMyProfileApplication.execute(foundUser, updateProfileRequestDto);
        return coreSuccessResponse(updateProfileResponseDto, "내 프로필 수정 성공", HttpStatus.OK.value());
    }

    private User createReturnUser(User sourceUser) {
        return User.builder()
                .id(sourceUser.getId())
                .email(sourceUser.getEmail())
                .name(sourceUser.getName())
                .age(sourceUser.getAge())
                .build();
    }
}