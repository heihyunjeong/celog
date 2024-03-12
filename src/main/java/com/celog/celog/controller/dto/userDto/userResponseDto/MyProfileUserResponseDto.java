package com.celog.celog.controller.dto.userDto.userResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class MyProfileUserResponseDto {
    Long id;
    String email;
    String name;
    Long age;
}
