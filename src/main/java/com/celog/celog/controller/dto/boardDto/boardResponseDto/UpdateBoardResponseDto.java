package com.celog.celog.controller.dto.boardDto.boardResponseDto;

import com.celog.celog.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class UpdateBoardResponseDto {
    Long id;
    String title;
    String content;
    String userEmail;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<Review> reviews;
}
