package com.example.firstproject.repository;


import com.example.firstproject.entity.Model;
import com.example.firstproject.entity.Partners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface ModelRepository extends JpaRepository<Model, String> {

    @Query(value = "SELECT * FROM model", nativeQuery = true)
    Page<Model> findModelBy(Pageable pageable);

    @Query(value =
            "SELECT  * " +
                    "FROM model " +
                    "WHERE model_code like ?1 " +
                    "OR model_name like ?1 ",
            nativeQuery = true)
    Page<Model> findBySearch(String searchTerm, Pageable pageable);

}
