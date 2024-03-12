package com.celog.celog.controller.dto.boardDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class CreateBoardRequestDto {
    @Email(message = "제목을 입력해주세요")
    @NotBlank(message = "제목을 입력해주세요")
    String title;

    @Email(message = "내용을 입력해주세요")
    @NotBlank(message = "내용을 입력해주세요")
    Long content;
}
