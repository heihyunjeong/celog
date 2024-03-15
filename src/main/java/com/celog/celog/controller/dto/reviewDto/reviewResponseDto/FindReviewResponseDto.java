package com.celog.celog.controller.dto.reviewDto.reviewResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class FindReviewResponseDto {
    private Long id;
    private String content;
    private String userEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
