package com.example.moduhouse.board.service;

import com.example.moduhouse.board.dto.BoardRequestDto;
import com.example.moduhouse.board.dto.BoardResponseDto;
import com.example.moduhouse.board.entity.Board;
import com.example.moduhouse.board.entity.BoardLike;
import com.example.moduhouse.board.entity.Category;
import com.example.moduhouse.board.entity.Url;
import com.example.moduhouse.board.repository.BoardLikeRepository;
import com.example.moduhouse.board.repository.BoardRepository;
import com.example.moduhouse.board.repository.UrlRepository;
import com.example.moduhouse.comment.dto.CommentResponseDto;
import com.example.moduhouse.comment.entity.Comment;
import com.example.moduhouse.global.MsgResponseDto;
import com.example.moduhouse.global.exception.CustomException;
import com.example.moduhouse.global.exception.ErrorCode;
import com.example.moduhouse.global.exception.SuccessCode;
import com.example.moduhouse.global.s3.S3Uploader;
import com.example.moduhouse.user.entity.User;
import com.example.moduhouse.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final S3Uploader s3Uploader;
    private final UrlRepository urlRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user, List<String> url) {

        if (Category.valueOfCategory(requestDto.getCategory()) == null) {
            throw new CustomException(ErrorCode.NO_EXIST_CATEGORY);
        }
        Board board = boardRepository.save(new Board(requestDto, user));
        for(String urls : url){
            urlRepository.save(new Url(urls, board));
        }
        return new BoardResponseDto(board);
    }


    @Transactional(readOnly = true)
    public List<BoardResponseDto> getListBoards(User user) {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();

        for (Board board : boardList) {
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : board.getComments()) {
                commentList.add(new CommentResponseDto(comment));
            }
            boardResponseDto.add(new BoardResponseDto(
                    board,
                    commentList,
                    (checkBoardLike(board.getId(), user))));
        }
        return boardResponseDto;
    }

    public List<BoardResponseDto> getCategoryListBoards(User user, String category) {
        if (Category.valueOfCategory(category) == null) {
            throw new CustomException(ErrorCode.NO_EXIST_CATEGORY);
        }
        List<Board> boardList = boardRepository.findAllByCategoryOrderByCreatedAtDesc(category);
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
        for (Board board : boardList) {
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : board.getComments()) {
                commentList.add(new CommentResponseDto(comment));
            }
            boardResponseDto.add(new BoardResponseDto(
                    board,
                    commentList,
                    (checkBoardLike(board.getId(), user))));
        }
        return boardResponseDto;
    }

    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
        );
        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : board.getComments()) {
            commentList.add(new CommentResponseDto(comment));
        }
        return new BoardResponseDto(
                board,
                commentList,
                (checkBoardLike(board.getId(), user)));
    }

    @Transactional
    public BoardResponseDto updateBoard(User user,Long id, BoardRequestDto requestDto,String url) {
        Board board;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            board = boardRepository.findById(id).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
            );
        } else {
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
            );
        }

        board.update(requestDto,url);

        List<CommentResponseDto> commentList = new ArrayList<>();

        for (Comment comment : board.getComments()) {
            commentList.add(new CommentResponseDto(comment));
        }

        if (Category.valueOfCategory(requestDto.getCategory()) == null) {
            throw new CustomException(ErrorCode.NO_EXIST_CATEGORY);
        }

        return new BoardResponseDto(
                board,
                commentList,
                (checkBoardLike(board.getId(), user)));

    }

    @Transactional
    public void deleteBoard(Long id, User user) {
        Board board;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            board = boardRepository.findById(id).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
            );
        } else {
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
            );
        }

        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    public boolean checkBoardLike(Long boardId, User user) {
        Optional<BoardLike> boardLike = boardLikeRepository.findByBoardIdAndUserId(boardId, user.getId());
        return boardLike.isPresent();
    }

    @Transactional
    public MsgResponseDto saveBoardLike(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
        );
        if(checkBoardLike(boardId,user)){
            throw new CustomException(ErrorCode.ALREADY_CLICKED_LIKE);
        }
            boardLikeRepository.saveAndFlush(new BoardLike(board, user));
            return new MsgResponseDto(SuccessCode.LIKE);
    }

    @Transactional
    public MsgResponseDto saveBoardCancelLike(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
        );
        if(!checkBoardLike(boardId,user)){
            throw new CustomException(ErrorCode.ALERADY_CANCEL_LIKE);
        }
        boardLikeRepository.deleteByBoardIdAndUserId(boardId, user.getId());
        return new MsgResponseDto(SuccessCode.CANCEL_LIKE);
    }
}