//package com.example.moduhouse.comment;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
//    Optional<CommentLike> findByUserIdAndCommentId(Long userId, Long commentId);
//
//    void deleteByUserIdAndCommentId(Long userId, Long commentId);
//
//}