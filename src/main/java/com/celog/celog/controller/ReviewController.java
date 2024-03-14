package com.celog.celog.controller;

import com.celog.celog.application.ReviewApplication.CreateReviewApplication;
import com.celog.celog.controller.dto.reviewDto.reviewRequestDto.CreateReviewRequestDto;
import com.celog.celog.domain.User;
import com.celog.celog.shared.CoreSuccessResponse;
import com.celog.celog.shared.service.SecurityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "review", description = "리뷰 API")
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final CreateReviewApplication createReviewAppliation;
    private final SecurityService securityService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CoreSuccessResponse createReview(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid CreateReviewRequestDto createReviewRequestDto
    ) {
        User foundUser = securityService.getAuthenticatedUser(httpServletRequest);
        createReviewAppliation.execute(foundUser, createReviewRequestDto);
        return CoreSuccessResponse.coreSuccessResponse(true, "리뷰 작성 성공", 201);
    }
}
