package com.celog.celog.controller.dto.userDto.userRequestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class LoginRequestDto {
    @Email(message = "이메일 형식을 지켜주세요")
    @NotBlank(message = "이메일을 입력해주세요")
    String email;

    @NotBlank(message = "password를 입력해주세요")
    String password;
}
