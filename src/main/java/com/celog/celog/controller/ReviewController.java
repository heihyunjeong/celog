package com.celog.celog.controller;

import com.celog.celog.application.ReviewApplication.CreateReviewApplication;
import com.celog.celog.application.ReviewApplication.UpdateReviewApplication;
import com.celog.celog.controller.dto.reviewDto.reviewRequestDto.CreateReviewRequestDto;
import com.celog.celog.controller.dto.reviewDto.reviewRequestDto.UpdateReviewRequestDto;
import com.celog.celog.domain.User;
import com.celog.celog.shared.CoreSuccessResponse;
import com.celog.celog.shared.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.celog.celog.shared.CoreSuccessResponse.coreSuccessResponse;

@RestController
@Tag(name = "review", description = "리뷰 API")
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final CreateReviewApplication createReviewAppliation;
    private final UpdateReviewApplication updateReviewApplication;
    private final SecurityService securityService;

    @PostMapping("{boardId}")
    @Operation(summary = "리뷰 작성", description = "게시글에 리뷰를 작성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public CoreSuccessResponse createReview(
            HttpServletRequest httpServletRequest,
            @PathVariable Long boardId,
            @RequestBody @Valid CreateReviewRequestDto createReviewRequestDto
    ) {
        User foundUser = securityService.getAuthenticatedUser(httpServletRequest);
        createReviewAppliation.execute(foundUser, boardId, createReviewRequestDto);
        return coreSuccessResponse(true, "리뷰 작성 성공", 201);
    }

    @PutMapping("/{boardId}")
    @Operation(summary = "리뷰 수정", description = "게시글에 작성된 리뷰를 수정합니다.")
    @ResponseStatus(HttpStatus.OK)
    public CoreSuccessResponse updateReview(
            HttpServletRequest httpServletRequest,
            @PathVariable Long boardId,
            @RequestBody @Valid UpdateReviewRequestDto updateReviewRequestDto
    ) {
        User foundUser = securityService.getAuthenticatedUser(httpServletRequest);
        updateReviewApplication.execute(foundUser, boardId, updateReviewRequestDto);
        return coreSuccessResponse(true, "리뷰 수정 성공", 200);
    }
}
