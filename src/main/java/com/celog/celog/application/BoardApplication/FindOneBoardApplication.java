package com.celog.celog.application.BoardApplication;

import com.celog.celog.controller.dto.boardDto.boardResponseDto.FindOneBoardResponseDto;
import com.celog.celog.controller.dto.reviewDto.reviewResponseDto.FindReviewResponseDto;
import com.celog.celog.domain.Board;
import com.celog.celog.domain.Review;
import com.celog.celog.repository.BoardRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindOneBoardApplication {
    private final BoardRepository boardRepository;

    public FindOneBoardResponseDto execute(Long boardId) {
         Board board = findBoardById(boardId);
        return convertBoardToResponseDto(board);
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new HttpExceptionCustom(
                        false,
                        "해당하는 board를 찾을 수 없습니다.",
                        HttpStatus.NOT_FOUND
                ));
    }

    private FindOneBoardResponseDto convertBoardToResponseDto(Board board) {
        return FindOneBoardResponseDto.builder()
                 .id(board.getId())
                 .title(board.getTitle())
                 .content(board.getContent())
                 .userEmail(board.getUser().getEmail())
                 .createdAt(board.getCreatedAt())
                 .updatedAt(board.getUpdatedAt())
                 .reviews(findReviewResponseDtos(board))
                 .build();
    }

    private List<FindReviewResponseDto> findReviewResponseDtos(Board board) {
        return board.getReviews()
                .stream()
                .map(this::convertReviewToResponseDto)
                .collect(Collectors.toList());
    }

    private FindReviewResponseDto convertReviewToResponseDto(Review review){
        return FindReviewResponseDto.builder()
                        .id(review.getId())
                        .content(review.getContent())
                        .userEmail(review.getUser().getEmail())
                        .createdAt(review.getCreatedAt())
                        .updatedAt(review.getUpdatedAt())
                        .build();
    }
}