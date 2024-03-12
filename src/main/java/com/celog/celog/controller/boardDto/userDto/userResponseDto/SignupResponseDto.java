package com.celog.celog.controller.boardDto.userDto.userResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class SignupResponseDto {
    String email;
}
