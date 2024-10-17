package com.example.firstproject.service;

import com.example.firstproject.entity.Part;
import com.example.firstproject.entity.Partners;
import com.example.firstproject.repository.PartRepository;
import com.example.firstproject.repository.PartnersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PartnersService {

    @Autowired
    private PartnersRepository partnersRepository;

    public Page<Partners> index(Pageable pageable) {
        return partnersRepository.findByPartners(pageable);
    }

    public Page<Partners> searchParts(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        return partnersRepository.findBySearch(searchPattern, pageable);
    }
}
