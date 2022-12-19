package com.example.moduhouse.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    SIGN_UP(HttpStatus.OK, "회원가입에 성공했습니다."),
    LOG_IN(HttpStatus.OK, "로그인에 성공했습니다"),
    LIKE(HttpStatus.OK, "좋아요 성공"),
    CANCEL_LIKE(HttpStatus.OK, "좋아요 취소"),
    DELETE_BOARD(HttpStatus.OK, "게시글을 삭제하였습니다"),
    DELETE_COMMENT(HttpStatus.OK, "댓글을 삭제하였습니다")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}