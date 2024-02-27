package com.celog.celog.controller.dto.userDto.userRequestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    @Schema(description = "email", example = "이메일")
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식을 지켜주세요")
    private String email;

    @Schema(description = "name", example = "name")
    @NotBlank(message = "name을 입력해주세요")
    private String name;

    @Schema(description = "password", example = "password")
    @NotBlank(message = "password를 입력해주세요")
    private String password;

    @Schema(description = "age", example = "나이")
    private Long age;
}
