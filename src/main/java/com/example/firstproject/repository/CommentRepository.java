package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

// Comment 엔티티의 기본 키(id) 타입을 나타냅니다.
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value =
            "SELECT * " +
                    "FROM comment " +
                    "WHERE article_id = :articleId",
            nativeQuery = true)
    // 네이티브 SQL 쿼리를 사용하여 특정 게시글에 달린 댓글들을 조회합니다.
    List<Comment> findByArticleId(Long articleId);
    // Spring Data JPA의 쿼리 메서드 기능을 사용하여 특정 닉네임을 가진 댓글을 조회합니다.
    List<Comment> findByNickname(String nickname);
}