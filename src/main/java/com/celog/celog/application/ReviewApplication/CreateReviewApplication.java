package com.celog.celog.application.ReviewApplication;

import com.celog.celog.controller.dto.reviewDto.reviewRequestDto.CreateReviewRequestDto;
import com.celog.celog.domain.Board;
import com.celog.celog.domain.Review;
import com.celog.celog.domain.User;
import com.celog.celog.repository.BoardRepository;
import com.celog.celog.repository.ReviewRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateReviewApplication {
    private final ReviewRepository reviewRepository;
    private final BoardRepository boardRepository;


    @Transactional
    public void execute(
            User user,
            Long boardId,
            CreateReviewRequestDto createReviewRequestDto
    ) {
        Review review = new Review();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(
                        () -> new HttpExceptionCustom(
                                false,
                                "게시글이 존재하지 않습니다.",
                                HttpStatus.NOT_FOUND
                        )
                );
        review.setBoard(board);
        review.setUser(user);
        review.setContent(createReviewRequestDto.getContent());
        reviewRepository.save(review);
    }
}
