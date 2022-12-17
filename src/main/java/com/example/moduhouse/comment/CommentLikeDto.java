package com.example.moduhouse.comment;

import javax.persistence.*;

public class CommentLikeDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Commnet와 연관관계
    @ManyToOne
    @JoinColumn(name="comment_id", nullable = false)
    private Comment commment;


    //User와 연관관계
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    //생성자
    public CommentLikeDto(Comment comment, User user) {
        this.commment = comment;
        this.user = user;
    }


}
