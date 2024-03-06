package com.celog.celog.controller.dto.userDto.userRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class UpdateProfileRequestDto {
    String name;
    String password;
    Long age;
}
