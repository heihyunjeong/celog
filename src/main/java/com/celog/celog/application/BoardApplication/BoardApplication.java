package com.celog.celog.application.BoardApplication;


import com.celog.celog.controller.boardDto.boardDto.BoardDto;
import com.celog.celog.domain.Board;
import com.celog.celog.repository.BoardRepository;
import com.celog.celog.shared.service.SecurityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardApplication {

    private final BoardRepository boardRepository;
    private final SecurityService securityService;
    @Transactional
    public void create(BoardDto boardDto, String foundUser) {
        Board board = new Board();
        board.setId(securityService.getUserIdFromToken(foundUser));
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        boardRepository.save(board);
        //return BoardDto.toDto(board);
    }
}
