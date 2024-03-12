package com.celog.celog.repository;


import com.celog.celog.domain.Board;
import com.celog.celog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {


}
