package com.example.firstproject.repository;

import com.example.firstproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegisterRepository extends JpaRepository<Users, String> {
    @Query(value =
            "SELECT * " +
                    "FROM users " +
                    "WHERE userid = :userid",
            nativeQuery = true)
    List<Users> findByUserId(String userid);

    // CustomUserDetailsService에서 사용
    Users findByUserid(String userid);
}
