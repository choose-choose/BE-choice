package com.example.moduhouse.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    // 댓글 '좋아요'기능
    @PostMapping("/api/comments/like/{id}")
    public ResponseDto commentLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentLikeService.commentLike(id, userDetailsImpl.getUser());
    }

    @DeleteMapping("/api/comments/like/{id}")
    public ResponseDto commentDislike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    return commentLikeService.commentDislike(id, userDetailsImpl.getUser());
    }


}
