package com.example.moduhouse.board.repository;

import com.example.moduhouse.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByCreatedAtDesc();
    List<Board> findAllByLocalOrderByCreatedAtDesc(String local);

    //게시글 수정, 삭제
    Optional<Board> findByIdAndUserId(Long id, Long userId);
}
