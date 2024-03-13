package com.celog.celog.application.BoardApplication;

import com.celog.celog.controller.dto.boardDto.boardResponseDto.FindOneBoardResponseDto;
import com.celog.celog.domain.Board;
import com.celog.celog.repository.BoardRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindOneBoardApplication {
    private final BoardRepository boardRepository;

    public FindOneBoardResponseDto execute(Long boardId) {
         Board board =  boardRepository.findById(boardId)
                .orElseThrow(() -> new HttpExceptionCustom(
                        false,
                        "해당하는 board를 찾을 수 없습니다.",
                        HttpStatus.NOT_FOUND
                ));
        return FindOneBoardResponseDto.builder()
                 .id(board.getId())
                 .title(board.getTitle())
                 .content(board.getContent())
                 .userEmail(board.getUser().getEmail())
                 .createdAt(board.getCreatedAt())
                 .updatedAt(board.getUpdatedAt())
                 .reviews(board.getReviews())
                 .build();
    }
}