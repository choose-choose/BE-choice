package com.example.moduhouse.board.controller;

import com.example.moduhouse.board.dto.BoardRequestDto;
import com.example.moduhouse.board.dto.BoardResponseDto;
import com.example.moduhouse.board.service.BoardService;
import com.example.moduhouse.global.MsgResponseDto;
import com.example.moduhouse.global.exception.SuccessCode;
import com.example.moduhouse.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class BoardController {

    private final BoardService boardService;

    //게시글 작성
    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto,userDetails.getUser());
    }

    @GetMapping("/boards")
    public List<BoardResponseDto> getListBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getListBoards(userDetails.getUser());
    }
//    @GetMapping("/boards/{category}")
//    public List<BoardResponseDto> getCategoryListBoards(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String category){
//        return boardService.getCategoryListBoards(userDetails.getUser(),category);
//    }



//    @GetMapping("/board/{id}")
//    public BoardResponseDto getBoards(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return boardService.getBoard(id, userDetails.getUser());
//    }

//    @PutMapping("/board/{id}")
//    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return boardService.updateBoard(id, requestDto, userDetails.getUser());
//    }

    @DeleteMapping("/board/{id}")
    public MsgResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(id,userDetails.getUser());
        return new MsgResponseDto(SuccessCode.DELETE_BOARD);
    }

    @PostMapping("/board/like/{boardId}")
    public ResponseEntity<MsgResponseDto> saveBoardLike(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(boardService.saveBoardLike(boardId, userDetails.getUser()));
    }
}