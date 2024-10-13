package com.example.firstproject.repository;


import com.example.firstproject.entity.Part;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PartRepository extends CrudRepository<Part, Long> {
    @Query(value =
            "SELECT top 100 * " +
                    "FROM part ",
            nativeQuery = true)

    ArrayList<Part> findPartBy();
}
