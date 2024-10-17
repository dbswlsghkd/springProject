package com.example.firstproject.repository;


import com.example.firstproject.entity.Partners;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnersRepository extends JpaRepository<Partners, String> {
    @Query(value =
            "SELECT * " +
                    "FROM partner ",
            nativeQuery = true)
    Page<Partners> findByPartners(Pageable pageable);

    @Query(value =
            "SELECT  * " +
                    "FROM partner " +
                    "WHERE partner_code like ?1 " +
                    "OR partner_name like ?1 " +
                    "OR partner_address like ?1 ",
            nativeQuery = true)
    Page<Partners> findBySearch(String searchTerm, Pageable pageable);

}

