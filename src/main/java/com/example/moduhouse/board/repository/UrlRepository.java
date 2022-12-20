package com.example.moduhouse.board.repository;


import com.example.moduhouse.board.entity.Board;
import com.example.moduhouse.board.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long> {
    List<Url> findByBoardId(Long boardId);

    @Transactional
    void deleteAllByBoard (Board board);
}