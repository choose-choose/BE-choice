//package com.example.moduhouse.comment;
//
//import org.apache.catalina.User;
//
//import javax.persistence.*;
//
//@Entity
//public class CommentLike {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "comment_id", nullable = false)
//    private Comment comment;
//
//    //User와 연관관계 필요
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//
//    //생성자
//    public CommentLike(Comment comment, User user) {
//        this.comment = comment;
//        this.user = user;
//    }
//
//}
