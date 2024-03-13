package com.celog.celog.controller.dto.boardDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateBoardResponseDto {
    Long id;
    String title;
    String content;
    String userEmail;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
