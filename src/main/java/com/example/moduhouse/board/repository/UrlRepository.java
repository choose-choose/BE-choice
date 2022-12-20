package com.example.moduhouse.board.repository;

import com.example.moduhouse.board.entity.Board;
import com.example.moduhouse.board.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UrlRepository extends JpaRepository<Url, String>{
    List<Url> findAllById();
}



