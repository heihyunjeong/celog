package com.celog.celog.controller.dto.userDto.userResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginResponseDto {
    String email;
    String accessToken;
}
