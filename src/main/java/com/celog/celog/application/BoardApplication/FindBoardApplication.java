package com.celog.celog.application.BoardApplication;

import com.celog.celog.controller.dto.boardDto.boardResponseDto.FindBoardResponseDto;
import com.celog.celog.domain.Board;
import com.celog.celog.domain.QBoard;
import com.celog.celog.repository.BoardRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindBoardApplication {
    private final BoardRepository boardRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final QBoard qBoard = QBoard.board;

    public FindBoardResponseDto execute(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        // 만약 search 가 존재한다면 search 를 통해 게시글을 찾고, 존재하지 않는다면 모든 게시글을 찾는다.
        return isSearchNotEmpty(search) ? getBoardListBySearchTerm(pageable, search) : getAllBoardLists(pageable);
    }

    private FindBoardResponseDto getBoardListBySearchTerm(Pageable pageable, String search) {
        List<Board> boardList = fetchBoardsWithSearch(pageable, search);
        long total = fetchTotalCountWithSearch(search);
        return buildFindBoardResponseDto(pageable, total, boardList);
    }

    private FindBoardResponseDto getAllBoardLists(Pageable pageable) {
        List<Board> boardList = fetchAllBoards(pageable);
        long total = fetchTotalCount();
        return buildFindBoardResponseDto(pageable, total, boardList);
    }

    private boolean isSearchNotEmpty(String search) {
        return search != null && !search.isEmpty();
    }

    private FindBoardResponseDto buildFindBoardResponseDto(Pageable pageable, long total, List<Board> boardList) {
        return FindBoardResponseDto.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .boards(boardList)
                .totalPages((int) Math.ceil((double) total / pageable.getPageSize()))
                .build();
    }

    private List<Board> fetchBoardsWithSearch(Pageable pageable, String search) {
        return jpaQueryFactory.select(qBoard)
                .from(qBoard)
                .where(qBoard.title.contains(search))
                .orderBy(qBoard.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private long fetchTotalCountWithSearch(String search) {
        return jpaQueryFactory
                .selectFrom(QBoard.board)
                .where(QBoard.board.title.contains(search))
                .fetchCount();
    }

    private List<Board> fetchAllBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        System.out.println("boards = " + boards.getContent());
        return boards.getContent();
    }

    private long fetchTotalCount() {
        return boardRepository.count();
    }
}
