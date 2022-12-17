package com.example.moduhouse.global;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class MsgResponseDto {
    private HttpStatus statusCode;
    private String msg;
    public MsgResponseDto(HttpStatus statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
