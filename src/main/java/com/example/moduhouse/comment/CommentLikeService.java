package com.example.moduhouse.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;


    //좋아요 기능
    @Transactional
    public ResponseDto commentLike(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NO_EXIST_COMMENT));

        if (commentLikeRepository.findByUserIdAndCommentId(user.getId(), comment.getId()).isEmpty()) {
            commentLikeRepository.save(new CommentLike(comment, user));
            return new ResponseDto(SuccessCode.LIKE);
        } else {
            commentLikeRepository.deleteByUserIdAndCommentId(user.getId(), comment.getId());
            return new ResponseDto(SuccessCode.CANCEL_LIKE);
        }
    }


    //좋아요 취소기능
    @Transactional
    public ResponseDto commentDislike(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NO_EXIST_COMMENT));

        if (commentLikeRepository.findByUserIdAndCommentId(user.getId(), comment.getId()).isEmpty()) {
            commentLikeRepository.delete(new CommentLike(comment, user));
            return new ResponseDto(SuccessCode.LIKE);
        } else {
            commentLikeRepository.deleteByUserIdAndCommentId(user.getId(), comment.getId());
            return new ResponseDto(SuccessCode.CANCEL_LIKE);
        }
    }
}
