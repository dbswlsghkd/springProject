package com.example.firstproject.repository;


import com.example.firstproject.entity.Part;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface PartRepository extends CrudRepository<Part, Long> {
    @Query(value =
            "SELECT top 100 * " +
                    "FROM part ",
            nativeQuery = true)

    ArrayList<Part> findPartBy();

    @Query(value =
            "SELECT  * " +
            "FROM part " +
            "WHERE part_code like ?1 " +
            "OR part_name like ?1 " +
            "OR part_std like ?1 ",
            nativeQuery = true)
    List<Part> findBySearch(String searchTerm);
}
