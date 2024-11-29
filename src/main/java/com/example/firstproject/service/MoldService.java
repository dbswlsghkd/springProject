package com.example.firstproject.service;

import com.example.firstproject.entity.Mold;
import com.example.firstproject.mapper.MoldMapper;
import com.example.firstproject.repository.MoldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MoldService {

    @Autowired
    private MoldRepository moldRepository;

    private final MoldMapper moldMapper;

    public Page<Mold> index(Pageable pageable) {
        // return partRepository.findPartBy(pageable);
        log.info("index==========> 여기 들어와" );
        return moldMapper.findMoldAll(pageable);
    }

    public Page<Mold> searchMold(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        // return partRepository.findBySearch(searchPattern, pageable);
        log.info("searchParts ==========> 여기 들어와" );
        return moldMapper.findBySearch(searchPattern, pageable);
    }

}
