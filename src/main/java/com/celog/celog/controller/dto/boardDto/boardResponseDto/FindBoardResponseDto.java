package com.celog.celog.controller.dto.boardDto.boardResponseDto;

import com.celog.celog.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
public class FindBoardResponseDto {
    int page;
    int size;
    List<Board> boards;
}