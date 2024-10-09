package com.example.firstproject.repository;


import com.example.firstproject.entity.Part;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PartRepository extends CrudRepository<Part, Long> {
    ArrayList<Part> findAll();
}
