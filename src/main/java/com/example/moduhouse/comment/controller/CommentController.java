package com.example.moduhouse.comment.controller;

import com.example.moduhouse.comment.dto.CommentRequestDto;
import com.example.moduhouse.comment.dto.CommentResponseDto;
import com.example.moduhouse.global.MsgResponseDto;
import com.example.moduhouse.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    public final com.example.moduhouse.comment.CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments/{id}")
    public CommentResponseDto saveComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.saveComment(id, commentRequestDto, userDetailsImpl.getUser());
    }

    //수정 (관리자는 모든 댓글 수정 가능. 일반회원은 본인글만 수정 가능)
    @PutMapping("/comments/{id}/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long id, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.updateComment(id, commentId, commentRequestDto, userDetailsImpl.getUser());
    }

    // 댓글 삭제 (관리자는 모든 댓글 삭제 가능. 일반회원은 본인글만 삭제 가능)
    @DeleteMapping("/comments/{id}/{commentId}")
    public MsgResponseDto deleteComment(@PathVariable Long id, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.deleteComment(id, commentId, userDetailsImpl.getUser());
    }
}
