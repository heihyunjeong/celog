package com.celog.celog.application.BoardApplication;


import com.celog.celog.controller.dto.boardDto.CreateBoardRequestDto;
import com.celog.celog.domain.Board;
import com.celog.celog.domain.User;
import com.celog.celog.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardApplication {

    private final BoardRepository boardRepository;
    @Transactional
    public Board execute(CreateBoardRequestDto boardDto, User user) {
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .user(user)
                .build();
        return boardRepository.save(board);
    }
}
