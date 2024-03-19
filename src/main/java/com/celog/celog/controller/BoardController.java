package com.celog.celog.controller;

import com.celog.celog.application.BoardApplication.*;
import com.celog.celog.controller.dto.boardDto.boardRequestDto.CreateBoardRequestDto;
import com.celog.celog.controller.dto.boardDto.boardRequestDto.UpdateBoardRequestDto;
import com.celog.celog.controller.dto.boardDto.boardResponseDto.CreateBoardResponseDto;
import com.celog.celog.controller.dto.boardDto.boardResponseDto.FindBoardResponseDto;
import com.celog.celog.controller.dto.boardDto.boardResponseDto.FindOneBoardResponseDto;
import com.celog.celog.controller.dto.boardDto.boardResponseDto.UpdateBoardResponseDto;
import com.celog.celog.domain.User;
import com.celog.celog.shared.CoreSuccessResponse;
import com.celog.celog.shared.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.celog.celog.shared.CoreSuccessResponse.coreSuccessResponse;

@RestController
@Tag(name = "board", description = "게시판 API")
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final CreateBoardApplication createBoardApplication;
    private final FindBoardApplication findBoardApplication;
    private final FindOneBoardApplication findOneBoardApplication;
    private final UpdateBoardApplication updateBoardApplication;
    private final DeleteBoardApplication deleteBoardApplication;
    private final SecurityService securityService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "게시글 작성", description = "게시글을 작성합니다.")
    public CoreSuccessResponse insertBoard(
            @RequestBody @Valid CreateBoardRequestDto createBoardRequestDto,
            HttpServletRequest httpServletRequest
    ) {
        User user = securityService.getAuthenticatedUser(httpServletRequest);
        CreateBoardResponseDto createBoardResponseDto = createBoardApplication.execute(createBoardRequestDto, user);
        return coreSuccessResponse(createBoardResponseDto,"게시글 작성 성공", 200);
    }

    @GetMapping("{boardId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시글 하나 조회", description = "게시글을 조회합니다.")
    public CoreSuccessResponse getBoard(
            @PathVariable Long boardId
    ) {
        FindOneBoardResponseDto findOneBoardResponseDto = findOneBoardApplication.execute(boardId);
        return coreSuccessResponse(findOneBoardResponseDto, "게시글 조회 성공", 200);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "페이지네이션과 함께 게시글들 조회", description = "게시글을 조회합니다.")
    public CoreSuccessResponse getBoards(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search
    ) {
        // page 와 size 는 1부터 시작
        FindBoardResponseDto findBoardResponseDto = findBoardApplication.execute(
                page,
                size,
                search
        );
        return coreSuccessResponse(findBoardResponseDto, "게시글 조회 성공", 200);
    }

    @PutMapping("{boardId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시글 업데이트", description = "게시글을 업데이트합니다.")
    public CoreSuccessResponse updateBoard(
            HttpServletRequest httpServletRequest,
            @PathVariable Long boardId,
            @RequestBody @Valid UpdateBoardRequestDto updateBoardRequestDto
    ) {
        User foundUser = securityService.getAuthenticatedUser(httpServletRequest);
        UpdateBoardResponseDto updateBoardResponseDto = updateBoardApplication.execute(
                foundUser,
                boardId,
                updateBoardRequestDto
        );
        return coreSuccessResponse(
                updateBoardResponseDto,
                "성공적으로 board 를 업데이트 했습니다.",
                HttpStatus.OK.value()
        );
    }

    @DeleteMapping("{boardId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    public CoreSuccessResponse deleteBoard(
            HttpServletRequest httpServletRequest,
            @PathVariable Long boardId
    ) {
        User foundUser = securityService.getAuthenticatedUser(httpServletRequest);
        deleteBoardApplication.execute(foundUser, boardId);
        return coreSuccessResponse(
                Objects.nonNull(foundUser),
                "성공적으로 board 를 삭제 했습니다.",
                HttpStatus.OK.value()
        );
    }
}
