package com.example.firstproject.repository;

import com.example.firstproject.entity.Mold;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MoldRepository extends JpaRepository<Mold, String> {

    @Query(value ="SELECT * FROM mold", nativeQuery = true)
    Page<Mold> findMoldAll(Pageable pageable);

    @Query(value = "SELECT * FROM mold where m_pcode like ?1 or m_part_code like ?1", nativeQuery = true)
    Page<Mold> findMoldByPcode(String searchTerm, Pageable pageable);
}
