package com.celog.celog.application.ReviewApplication;

import com.celog.celog.controller.dto.reviewDto.reviewRequestDto.CreateReviewRequestDto;
import com.celog.celog.domain.Review;
import com.celog.celog.domain.User;
import com.celog.celog.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateReviewApplication {
    private final ReviewRepository reviewRepository;

    @Transactional
    public void execute(
            User user,
            CreateReviewRequestDto createReviewRequestDto
    ) {
        Review review = new Review();
        review.setBoard(createReviewRequestDto.getBoard());
        review.setUser(user);
        review.setContent(createReviewRequestDto.getContent());
        reviewRepository.save(review);
    }
}
