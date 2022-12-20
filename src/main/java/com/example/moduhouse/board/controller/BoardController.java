package com.example.moduhouse.board.controller;

import com.example.moduhouse.board.dto.BoardRequestDto;
import com.example.moduhouse.board.dto.BoardResponseDto;
import com.example.moduhouse.board.entity.Url;
import com.example.moduhouse.board.repository.BoardRepository;
import com.example.moduhouse.board.repository.UrlRepository;
import com.example.moduhouse.board.service.BoardService;
import com.example.moduhouse.global.MsgResponseDto;
import com.example.moduhouse.global.exception.SuccessCode;
import com.example.moduhouse.global.s3.S3Uploader;
import com.example.moduhouse.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class BoardController {

    private final BoardService boardService;
    private final S3Uploader s3Uploader;
    private final BoardRepository boardRepository;
    private final UrlRepository urlRepository;

    @PostMapping(value = "/board", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BoardResponseDto createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @RequestPart BoardRequestDto request,
                                        @RequestPart("image") List<MultipartFile> multipartFile) throws IOException {
        List<String> url = new ArrayList<>();

        for (MultipartFile multipart : multipartFile) {
            if (multipart.isEmpty()) {
                url.add("");
            } else {
                url.add(s3Uploader.upload(userDetails.getUser(), request, multipart, "static"));
            }
        }

        return boardService.createBoard(request, userDetails.getUser(), url);
    }


    @PutMapping("/board/{id}")
    public BoardResponseDto updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @PathVariable Long id,
                                        @RequestPart BoardRequestDto requestDto,
                                        @RequestPart("image") List<MultipartFile> multipartFile) throws IOException {
        List<String> url = new ArrayList<>();
        boolean blank = false;
        for (MultipartFile multipart : multipartFile){
            if(multipart.isEmpty()){
                List<Url> urls = urlRepository.findByBoardId(id);
                for(Url selectUrl : urls){
                    url.add(selectUrl.getUrl());
                    blank = true;
                }
            } else{
                url.add(s3Uploader.upload(userDetails.getUser(), requestDto, multipart, "static"));
            }
        }
        return boardService.updateBoard(userDetails.getUser(), id, requestDto, url, blank);
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