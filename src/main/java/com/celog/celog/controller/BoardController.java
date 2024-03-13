package com.celog.celog.controller;

import com.celog.celog.application.BoardApplication.BoardApplication;
import com.celog.celog.controller.dto.boardDto.CreateBoardRequestDto;
import com.celog.celog.controller.dto.boardDto.CreateBoardResponseDto;
import com.celog.celog.domain.Board;
import com.celog.celog.domain.User;
import com.celog.celog.shared.CoreSuccessResponse;
import com.celog.celog.shared.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.celog.celog.shared.CoreSuccessResponse.coreSuccessResponse;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardApplication boardApplication;
    private final SecurityService securityService;

    @PostMapping("")
    public CoreSuccessResponse insertBoard(@RequestBody @Valid CreateBoardRequestDto createBoardRequestDto, HttpServletRequest httpServletRequest) throws Exception{
        User user = securityService.getAuthenticatedUser(httpServletRequest.getHeader("Authorization"));
        CreateBoardResponseDto createBoardResponseDto = boardApplication.execute(createBoardRequestDto, user);

        return coreSuccessResponse(createBoardResponseDto,"게시글 작성 성공", 200);
    }


    // 전체 게시글 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boardList")
    public CoreSuccessResponse getBoards() {
        List<CreateBoardRequestDto> responseBoardList = boardApplication.showBoardList();
        return coreSuccessResponse(responseBoardList,"전체 게시물 보기", 200);
    }

}
