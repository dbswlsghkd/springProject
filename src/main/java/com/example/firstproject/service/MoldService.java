package com.example.firstproject.service;

import com.example.firstproject.entity.Mold;
import com.example.firstproject.repository.MoldRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MoldService {

    @Autowired
    private MoldRepository moldRepository;

    public Page<Mold> index(Pageable pageable) {
        return moldRepository.findMoldAll(pageable);
    }

    public Page<Mold> searchMold(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        return moldRepository.findMoldByPcode(searchPattern, pageable);
    }

}
