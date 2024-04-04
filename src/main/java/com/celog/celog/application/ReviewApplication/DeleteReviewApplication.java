package com.celog.celog.application.ReviewApplication;

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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeleteReviewApplication {
    private final ReviewRepository reviewRepository;

    @Transactional
    public void execute(
            User user,
            Long reviewId
    ) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new HttpExceptionCustom(
                        false,
                        "해당하는 리뷰를 찾을 수 없습니다.",
                        HttpStatus.NOT_FOUND
                ));

        if (!review.getUser().equals(user)) {
            throw new HttpExceptionCustom(
                    false,
                    "해당 리뷰를 삭제할 권한이 없습니다.",
                    HttpStatus.FORBIDDEN
            );
        }

        review.setDeletedAt(LocalDateTime.now());
    }
}
