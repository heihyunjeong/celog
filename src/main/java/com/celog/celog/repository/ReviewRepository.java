package com.celog.celog.repository;

import com.celog.celog.domain.Board;
import com.celog.celog.domain.Review;
import com.celog.celog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByBoardAndUser(Board board, User user);

    Page<Review> findByBoard(Board board, Pageable pageable);
}
