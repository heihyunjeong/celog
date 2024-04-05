package com.celog.celog.application.ReviewApplication;

import com.celog.celog.controller.dto.reviewDto.reviewRequestDto.CreateReviewRequestDto;
import com.celog.celog.controller.dto.reviewDto.reviewRequestDto.UpdateReviewRequestDto;
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

@Service
@RequiredArgsConstructor
public class UpdateReviewApplication {
    private final ReviewRepository reviewRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void execute(
            User user,
            Long reviewId,
            UpdateReviewRequestDto updateReviewRequestDto
    ) {
        Review foundReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new HttpExceptionCustom(
                        false,
                        "해당하는 리뷰를 찾을 수 없습니다.",
                        HttpStatus.NOT_FOUND
                ));

        if(!foundReview.getBoard().getUser().equals(user)){
            throw new HttpExceptionCustom(
                    false,
                    "해당 리뷰를 수정할 권한이 없습니다.",
                    HttpStatus.FORBIDDEN
            );
        }

        foundReview.setContent(updateReviewRequestDto.getContent());
    }
}
