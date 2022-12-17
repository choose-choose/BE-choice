package com.example.moduhouse.user.entity;

import com.example.moduhouse.board.entity.Board;
import com.example.moduhouse.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

     //Board(게시글) 이 참조하는 관계
    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    // Comment(댓글) 이 참조하는 관계
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();


//
//    @OneToMany(mappedBy = "user")
//    private List<BoardLike> boardLikes = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<CommentLike> commentLikes = new ArrayList<>();

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
