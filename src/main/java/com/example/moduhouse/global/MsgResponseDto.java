package com.example.moduhouse.global;

<<<<<<< HEAD
public class MsgResponseDto {
    private String msg;
    private int statuscode;
=======
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
>>>>>>> 212268fc228b4dea46530775be853b6254d2c6b3
}
