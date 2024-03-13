package com.celog.celog.controller.dto.boardDto.boardRequestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UpdateBoardRequestDto {
    @NotBlank(message = "아이디값을 입력해주세요")
    Long id;

    @NotBlank(message = "제목을 입력해주세요")
    String title;

    @NotBlank(message = "내용을 입력해주세요")
    String content;
}
