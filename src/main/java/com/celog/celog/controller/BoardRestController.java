package com.celog.celog.controller;

import com.celog.celog.application.BoardApplication.BoardApplication;
import com.celog.celog.application.UserApplication.SignupUserApplication;
import com.celog.celog.controller.boardDto.boardDto.BoardDto;
import com.celog.celog.controller.boardDto.userDto.userRequestDto.SignupRequestDto;
import com.celog.celog.domain.User;
import com.celog.celog.repository.UserRepository;
import com.celog.celog.shared.AuthenticationConfig;
import com.celog.celog.shared.CoreSuccessResponse;
import com.celog.celog.shared.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.celog.celog.shared.CoreSuccessResponse.coreSuccessResponse;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardRestController {
    private final BoardApplication boardApplication;
    private final UserRepository userRepository;
    private final SignupUserApplication signupUserApplication;
    private final SecurityService securityService;

    @PostMapping("/board/write")
    public void insertBoard(@RequestBody @Valid BoardDto board, HttpServletRequest httpServletRequest) throws Exception{

        User foundUser = securityService.getLogin(httpServletRequest.getHeader("Authorization"));

        boardApplication.create(board, String.valueOf(foundUser));

        //return coreSuccessResponse(signupUserApplication.run(String.valueOf(foundUser)), "게시판 쓰기", HttpStatus.CREATED.value());
    }

}
