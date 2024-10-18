package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import com.example.firstproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoginRepository extends JpaRepository<Users, String> {
    @Query(value =
            "SELECT * " +
                    "FROM users " +
                    "WHERE userid = ?1 and psword = ?2",
            nativeQuery = true)
        // 네이티브 SQL 쿼리를 사용하여 특정 게시글에 달린 댓글들을 조회합니다.
    List<Users> findByUserIdAndPsword(String userid, String psword);

    @Query(value = "SELECT * FROM users WHERE userid = ?1", nativeQuery = true)
    Users findByUserId(String userid);

}
