package com.celog.celog.controller.dto.boardDto.boardRequestDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FindOneBoardRequestDto {
    Long boardId;
}
