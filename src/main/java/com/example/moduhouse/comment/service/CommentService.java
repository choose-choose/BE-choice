package com.example.moduhouse.comment.service;

import com.example.moduhouse.board.entity.Board;
import com.example.moduhouse.board.repository.BoardRepository;
import com.example.moduhouse.comment.dto.CommentRequestDto;
import com.example.moduhouse.comment.dto.CommentResponseDto;
import com.example.moduhouse.comment.entity.Comment;
import com.example.moduhouse.comment.repository.CommentRepository;
import com.example.moduhouse.global.MsgResponseDto;
import com.example.moduhouse.global.exception.CustomException;
import com.example.moduhouse.global.exception.ErrorCode;
import com.example.moduhouse.global.exception.SuccessCode;
import com.example.moduhouse.user.entity.User;
import com.example.moduhouse.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto saveComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NO_BOARD_FOUND)
        );

        Comment comment = new Comment(commentRequestDto, board, user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public CommentResponseDto updateComment(Long id, Long commentId, CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NO_BOARD_FOUND));

        Comment comment;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            // ADMIN 권한일 때
            comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.NO_EXIST_COMMENT));
        } else {
            // User 권한일 때
            comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(() -> new CustomException(ErrorCode.NO_MODIFY_COMMENT));

        }
        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    public MsgResponseDto deleteComment(Long id, Long commentId, User user) {
        boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NO_BOARD_FOUND));

        Comment comment;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            // ADMIN 권한일 때
            comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.NO_EXIST_COMMENT));
        } else {
            // User 권한일 때
            comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(() -> new CustomException(ErrorCode.NO_DELETE_COMMENT));
        }

        commentRepository.delete(comment);
        return new MsgResponseDto(SuccessCode.DELETE_COMMENT);
    }
}