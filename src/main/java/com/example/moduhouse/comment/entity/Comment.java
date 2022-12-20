package com.example.moduhouse.comment.entity;

import com.example.moduhouse.board.entity.Board;
import com.example.moduhouse.comment.dto.CommentRequestDto;
import com.example.moduhouse.global.entity.Timestamped;
import com.example.moduhouse.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String comment;

    //Board와 연관관계 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    //User과 연관관계 필요
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    //생성자
public Comment(CommentRequestDto commentRequestDto, Board board, User user) {
    this.comment =commentRequestDto.getComment();
    this.username = user.getUsername();
    this.board = board;
    this.user = user;
}

    //메서드
    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }

}
