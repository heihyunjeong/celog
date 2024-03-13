package com.celog.celog.application.BoardApplication;


import com.celog.celog.controller.dto.boardDto.CreateBoardRequestDto;
import com.celog.celog.controller.dto.boardDto.CreateBoardResponseDto;
import com.celog.celog.domain.Board;
import com.celog.celog.domain.User;
import com.celog.celog.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardApplication {

    private final BoardRepository boardRepository;
    @Transactional
    public CreateBoardResponseDto execute(CreateBoardRequestDto boardDto, User user) {
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .user(user)
                .build();
        Board saved = boardRepository.save(board);
        return CreateBoardResponseDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .userEmail(saved.getUser().getEmail())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    public List<CreateBoardRequestDto> showBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<CreateBoardRequestDto> boardDtosList = new ArrayList<>();
        for(Board board : boardList){
            CreateBoardRequestDto dto = CreateBoardRequestDto.builder()
                    .title(board.getTitle())
                    .content(board.getContent())
                    .build();
            boardDtosList.add(dto);
        }
        return boardDtosList;
    }
}
