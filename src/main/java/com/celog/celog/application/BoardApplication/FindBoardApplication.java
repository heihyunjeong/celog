package com.celog.celog.application.BoardApplication;

import com.celog.celog.controller.dto.boardDto.boardResponseDto.FindBoardResponseDto;
import com.celog.celog.domain.Board;
import com.celog.celog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindBoardApplication {
    private final BoardRepository boardRepository;

    public FindBoardResponseDto execute(
           int page,
           int size,
           String search
    ) {
        // 서비스에서 호출
        Pageable pageable = PageRequest.of(page, size); // 정렬 기준 추가 필요
        Page<Board> boards;
        if (search != null && !search.isEmpty()) {
            boards = boardRepository.findByTitleContainingAndSort(search, pageable);
        } else {
            boards = boardRepository.findAll(pageable);
        }

        return FindBoardResponseDto.builder()
                .page(page)
                .size(size)
                .boards(boards.getContent())
                .build();
    }
}
