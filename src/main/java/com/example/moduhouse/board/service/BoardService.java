package com.example.moduhouse.board.service;

import com.example.moduhouse.board.dto.BoardRequestDto;
import com.example.moduhouse.board.dto.BoardResponseDto;
import com.example.moduhouse.board.entity.Board;
import com.example.moduhouse.board.entity.BoardLike;
import com.example.moduhouse.board.entity.Category;
import com.example.moduhouse.board.repository.BoardLikeRepository;
import com.example.moduhouse.board.repository.BoardRepository;
import com.example.moduhouse.global.MsgResponseDto;
import com.example.moduhouse.global.exception.CustomException;
import com.example.moduhouse.global.exception.ErrorCode;
import com.example.moduhouse.global.exception.SuccessCode;
import com.example.moduhouse.user.entity.User;
import com.example.moduhouse.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
//    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        if (Category.valueOfCategory(requestDto.getCategory()) == null) {
            return null;
        }
        Board board = boardRepository.save(new Board(requestDto, user));
        return new BoardResponseDto(board);
    }


    @Transactional(readOnly = true)
    public List<BoardResponseDto> getListBoards(User user) {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();

        for (Board board : boardList) {
//            List<CommentResponseDto> commentList = new ArrayList<>();
//            for (Comment comment : board.getComments()) {
//                commentList.add(new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId())));
//            }
            boardResponseDto.add(new BoardResponseDto(
                    board,(checkBoardLike(board.getId(), user))));
//                    commentList,
//                    (checkBoardLike(board.getId(), user))));
        }
        return boardResponseDto;
    }

//    public List<BoardResponseDto> getCategoryListBoards(User user, String category) {
//        if (Category.valueOfCategory(category) == null) {
//            throw new CustomException(NOT_EXIST_CATEGORY);
//        }
//        List<Board> boardList = boardRepository.findAllByCategoryOrderByCreatedAtDesc(category);
//        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
//        for (Board board : boardList) {
//            List<CommentResponseDto> commentList = new ArrayList<>();
//            for (Comment comment : board.getComments()) {
//                commentList.add(new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId())));
//            }
//            boardResponseDto.add(new BoardResponseDto(
//                    board,
//                    commentList,
//                    (checkBoardLike(board.getId(), user))));
//        }
//        return boardResponseDto;
//    }

//    @Transactional(readOnly = true)
//    public BoardResponseDto getBoard(Long id, User user) {
//        Board board = boardRepository.findById(id).orElseThrow(
//                () -> new CustomException(NOT_FOUND_USER)
//        );
//        List<CommentResponseDto> commentList = new ArrayList<>();
//        for (Comment comment : board.getComments()) {
//            commentList.add(new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId())));
//        }
//        return new BoardResponseDto(
//                board,
//                commentList,
//                (checkBoardLike(board.getId(), user)));
//    }

//    @Transactional
//    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto, User user) {
//        Board board;
//        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
//            board = boardRepository.findById(id).orElseThrow(
//                    () -> new IllegalArgumentException("1")
//            );
//        } else {
//            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
//                    () -> new IllegalArgumentException("1")
//            );
//        }
//        board.update(requestDto);
////        List<CommentResponseDto> commentList = new ArrayList<>();
////        for (Comment comment : board.getComments()) {
////            commentList.add(new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId())));
////        }
////        if (requestDto.getCategory().equals("TIL") || requestDto.getCategory().equals("Spring") || requestDto.getCategory().equals("Java")) {
////            return new BoardResponseDto(
////                    board,
////                    commentList,
////                    (checkBoardLike(board.getId(), user)));
////        } else {
////            throw new CustomException(NOT_EXIST_CATEGORY);
////        }
//
//    }

    @Transactional
    public void deleteBoard(Long id, User user) {
        Board board;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("1")
            );
        } else {
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("1")
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
        if (!checkBoardLike(boardId, user)) {
            boardLikeRepository.saveAndFlush(new BoardLike(board, user));
            return new MsgResponseDto(SuccessCode.LIKE);
        } else {
            boardLikeRepository.deleteByBoardIdAndUserId(boardId, user.getId());
            return new MsgResponseDto(SuccessCode.CANCEL_LIKE);
        }
    }


}