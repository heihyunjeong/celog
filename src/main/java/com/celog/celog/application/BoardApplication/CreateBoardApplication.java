package com.celog.celog.application.BoardApplication;


import com.celog.celog.controller.dto.boardDto.boardRequestDto.CreateBoardRequestDto;
import com.celog.celog.controller.dto.boardDto.boardResponseDto.CreateBoardResponseDto;
import com.celog.celog.domain.Board;
import com.celog.celog.domain.User;
import com.celog.celog.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBoardApplication {

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
}
