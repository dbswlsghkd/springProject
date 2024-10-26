package com.example.firstproject.repository;

import com.example.firstproject.entity.Part;
import com.example.firstproject.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    @Query(value = "select * from users", nativeQuery = true)
    Page<Users> getAllUsers(Pageable pageable);

    @Query(value =
            "SELECT  * " +
                    "FROM users " +
                    "WHERE userid like ?1 " +
                    "OR name like ?1 " +
                    "OR role like ?1 ",
            nativeQuery = true)
    Page<Users> findBySearch(String searchTerm, Pageable pageable);
}
