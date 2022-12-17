package com.example.moduhouse.board.dto;

import com.example.moduhouse.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    //필드
    private Long id;
    private String title;
    private String category;
    private String contents;
    private String username;
    private int boardLikeCount;
    private boolean boardLikeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    //생성자
    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.category = board.getCategory();
        this.contents = board.getContents();
        this.username = board.getUsername();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
//    private List<CommentResponseDto> commentList = new ArrayList<>();
//  List<CommentResponseDto> commentList,
    public BoardResponseDto(Board board,  boolean boardLikeCheck) {
        this.id = board.getId();            //this.id: (위에서 선언된) 필드, Board 객체의 board 매개변수로 들어온 데이터를 getId() 에 담는다(Client 에게로 보내기 위해)
        this.title = board.getTitle();
        this.category = board.getCategory();
        this.contents = board.getContents();
        this.username = board.getUsername();
        this.boardLikeCount = board.getBoardLikeList().size();
        this.boardLikeCheck = boardLikeCheck;
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
//        this.commentList = commentList;
    }
}