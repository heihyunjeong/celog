package com.celog.celog.controller.dto.reviewDto.reviewRequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateReviewRequestDto {
    @NotNull
    Long boardId;

    @NotNull
    Long userId;

    @NotBlank(message = "제목을 입력해주세요")
    String content;
}
