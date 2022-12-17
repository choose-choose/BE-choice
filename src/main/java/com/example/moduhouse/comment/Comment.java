package com.example.moduhouse.comment;


import com.example.moduhouse.comment.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String comment;

    //commentLikes와 연관관계 필요
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentLike> commentLikes = new ArrayList<>();

    //Board와 연관관계 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    //User과 연관관계 필요
    @Column(nullable = false)
    @JoinColumn(name = "user_id")
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
