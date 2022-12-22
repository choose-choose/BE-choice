package com.example.moduhouse.board.repository;

import com.example.moduhouse.board.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByBoardId(Long boardId);
    List<Image> deleteAllByBoardId(Long boardId);

}

