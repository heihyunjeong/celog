package com.celog.celog.application.BoardApplication;


import com.celog.celog.domain.Board;
import com.celog.celog.domain.User;
import com.celog.celog.repository.BoardRepository;
import com.celog.celog.shared.Exception.HttpExceptionCustom;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Builder
@Service
public class DeleteBoardApplication {
    private final BoardRepository boardRepository;

    public void execute(
            User user,
            Long boardId
    ) {
        Board board = boardRepository.findBoardByIdAndUser(boardId, user)
                .orElseThrow(() -> new HttpExceptionCustom(
                        false,
                        "해당하는 board를 찾을 수 없습니다.",
                        HttpStatus.NOT_FOUND
                ));
        board.setDeletedAt(LocalDateTime.now());
    }
}
