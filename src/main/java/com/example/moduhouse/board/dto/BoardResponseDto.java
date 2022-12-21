package com.example.moduhouse.board.dto;

import com.example.moduhouse.board.entity.Board;
import com.example.moduhouse.comment.dto.CommentResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    //필드
    private Long id;
    private String title;
    private String local;
    private String content;
    private String username;
    private int boardLike;
    private boolean boardLikeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<String> imageList = new ArrayList<>();

    //생성자
    public BoardResponseDto(Board board, List<String> images) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.local = board.getLocal();
        this.content = board.getContents();
        this.username = board.getUsername();
        this.imageList = images;
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    private List<CommentResponseDto> commentList = new ArrayList<>();
    public BoardResponseDto(Board board, List<CommentResponseDto> commentList, boolean boardLikeCheck, List<String> images) {
        this.id = board.getId();            //this.id: (위에서 선언된) 필드, Board 객체의 board 매개변수로 들어온 데이터를 getId() 에 담는다(Client 에게로 보내기 위해)
        this.title = board.getTitle();
        this.local = board.getLocal();
        this.content = board.getContents();
        this.username = board.getUsername();
        this.imageList = images;
        this.boardLike = board.getBoardLikeList().size();
        this.boardLikeCheck = boardLikeCheck;
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.commentList = commentList;
    }
}