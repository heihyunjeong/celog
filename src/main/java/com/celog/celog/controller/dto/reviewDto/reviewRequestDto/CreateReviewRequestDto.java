package com.celog.celog.controller.dto.reviewDto.reviewRequestDto;

import com.celog.celog.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateReviewRequestDto {
    Board board;
    String content;
}
