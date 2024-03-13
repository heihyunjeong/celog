package com.celog.celog.application.BoardApplication;

import com.celog.celog.controller.dto.boardDto.boardRequestDto.CreateBoardRequestDto;
import com.celog.celog.controller.dto.boardDto.boardRequestDto.UpdateBoardRequestDto;
import com.celog.celog.controller.dto.boardDto.boardResponseDto.UpdateBoardResponseDto;
import com.celog.celog.domain.Board;
import com.celog.celog.domain.User;
import com.celog.celog.repository.BoardRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateBoardApplication {
    private final BoardRepository boardRepository;

    public UpdateBoardResponseDto execute(
            User user,
            Long boardId,
            UpdateBoardRequestDto updateBoardRequestDto
    ) {
        String title = updateBoardRequestDto.getTitle();
        String content = updateBoardRequestDto.getContent();

        Board foundBoard = boardRepository.findBoardByIdAndUser(boardId,user)
                .orElseThrow(() -> new HttpExceptionCustom(
                        false,
                        "해당하는 board를 찾을 수 없습니다.",
                        HttpStatus.NOT_FOUND
                ));
        foundBoard.setTitle(title);
        foundBoard.setContent(content);

        return UpdateBoardResponseDto.builder()
                .id(foundBoard.getId())
                .title(foundBoard.getTitle())
                .content(foundBoard.getContent())
                .userEmail(foundBoard.getUser().getEmail())
                .createdAt(foundBoard.getCreatedAt())
                .updatedAt(foundBoard.getUpdatedAt())
                .reviews(foundBoard.getReviews())
                .build();
    }
}
