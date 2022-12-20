package com.example.moduhouse.board.controller;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.example.moduhouse.board.dto.BoardRequestDto;
import com.example.moduhouse.board.dto.BoardResponseDto;
import com.example.moduhouse.board.repository.BoardRepository;
import com.example.moduhouse.board.service.BoardService;
import com.example.moduhouse.global.MsgResponseDto;
import com.example.moduhouse.global.exception.SuccessCode;
import com.example.moduhouse.global.s3.S3Uploader;
import com.example.moduhouse.global.security.UserDetailsImpl;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;
    private final S3Uploader s3Uploader;

    @PostMapping(value = "/board", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BoardResponseDto createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @RequestPart BoardRequestDto request,
                              @RequestPart("image") List<MultipartFile> multipartFile) throws IOException {
        List<String> url = new ArrayList<>();
        for(MultipartFile multipart : multipartFile){
            if(multipartFile.isEmpty()){
                url.add("");
            }else{
                url.add(s3Uploader.upload(userDetails.getUser(),request,multipart,"static"));
            }
        }
      return  boardService.createBoard(request,userDetails.getUser(),url);
    }

    @GetMapping("/boards")
    public List<BoardResponseDto> getListBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getListBoards(userDetails.getUser());
    }

    @GetMapping("/boards/{category}")
    public List<BoardResponseDto> getCategoryListBoards(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String category) {
        return boardService.getCategoryListBoards(userDetails.getUser(), category);
    }

    @GetMapping("/board/{id}")
    public BoardResponseDto getBoards(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getBoard(id, userDetails.getUser());
    }


    @DeleteMapping("/board/{id}")
    public MsgResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(id, userDetails.getUser());

        List<String> url = new ArrayList<>();



        return new MsgResponseDto(SuccessCode.DELETE_BOARD);
    }


    @PostMapping("/board/{boardId}/boardlike")
    public ResponseEntity<MsgResponseDto> saveBoardLike(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(boardService.saveBoardLike(boardId, userDetails.getUser()));
    }

    @DeleteMapping("/board/{boardId}/boardCancelLike")
    public ResponseEntity<MsgResponseDto> saveBoardCancelLike(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(boardService.saveBoardCancelLike(boardId, userDetails.getUser()));
    }
}