//package com.example.moduhouse.comment.dto;
//
//import com.example.moduhouse.comment.Comment;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Getter
//@NoArgsConstructor
//public class CommentResponseDto {
//    private Long commentId;
//    private Long postId;
//    private String username;
//    private String comment;
//    private LocalDateTime modifiedat;
//    private LocalDateTime createdat;
//    private int commentLikeCnt;
//
//    //생성자
//    public CommentResponseDto(Comment comment) {
//        this.postId = comment.getPost().getId();
//        this.commentId = comment.getId();
//        this.username = comment.getUsername();
//        this.comment = comment.getComment();
//        this.modifiedat = comment.getModifiedAt();
//        this.createdat = comment.getModifiedAt();
//        this.commentLikeCnt = comment.getCommentLikes().size();
//    }
//}
