package com.celog.celog.repository;


import com.celog.celog.domain.Board;
import com.celog.celog.domain.Review;
import com.celog.celog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // JpaRepository
    @Query("SELECT b FROM Board b WHERE b.title LIKE %:search% ORDER BY b.createdAt desc ")
    Page<Board> findByTitleContainingAndSort(@Param("search") String search, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.id = :boardId AND b.user = :user")
    Optional<Board> findBoardByIdAndUser(@Param("boardId") Long boardId, @Param("user") User user);
}
