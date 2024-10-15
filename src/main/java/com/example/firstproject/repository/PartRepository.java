package com.example.firstproject.repository;


import com.example.firstproject.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    @Query(value =
            "SELECT * " +
                    "FROM part ",
            nativeQuery = true)

    Page<Part> findPartBy(Pageable pageable);

    @Query(value =
            "SELECT  * " +
            "FROM part " +
            "WHERE part_code like ?1 " +
            "OR part_name like ?1 " +
            "OR part_std like ?1 ",
            nativeQuery = true)
    Page<Part> findBySearch(String searchTerm, Pageable pageable);
}
