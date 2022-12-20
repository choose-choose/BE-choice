package com.example.moduhouse.board.entity;

import com.example.moduhouse.board.dto.BoardRequestDto;
import com.example.moduhouse.comment.entity.Comment;
import com.example.moduhouse.global.entity.Timestamped;
import com.example.moduhouse.user.entity.User;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name = "Board")
@NoArgsConstructor
@AllArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @OrderBy("createdAt DESC")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardLike> boardLikeList = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column
    private String category;

    @Column
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Board(BoardRequestDto requestDto, User user, String url) {
        this.title = requestDto.getTitle();
        this.username = user.getUsername();
        this.contents = requestDto.getContent();
        this.category = requestDto.getCategory();
        this.user = user;
        this.url = url;
    }


    public void update(BoardRequestDto boardrequestDto) {
        this.title = boardrequestDto.getTitle();
        this.category = boardrequestDto.getCategory();
        this.contents = boardrequestDto.getContent();
    }
}