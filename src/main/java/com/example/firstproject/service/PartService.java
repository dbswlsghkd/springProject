package com.example.firstproject.service;

import com.example.firstproject.entity.Part;
import com.example.firstproject.repository.PartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Slf4j
public class PartService {

    @Autowired
    private PartRepository partRepository;

    public Page<Part> index(Pageable pageable) {
        return partRepository.findPartBy(pageable);
    }

    public Page<Part> searchParts(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        return partRepository.findBySearch(searchPattern, pageable);
    }

}
