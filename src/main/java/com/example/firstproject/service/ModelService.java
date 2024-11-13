package com.example.firstproject.service;

import com.example.firstproject.entity.Model;
import com.example.firstproject.entity.Partners;
import com.example.firstproject.repository.ModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    public Page<Model> index(Pageable pageable) {
        return modelRepository.findModelBy(pageable);
    }

    public Page<Model> searchParts(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        return modelRepository.findBySearch(searchPattern, pageable);
    }

    // 등록
    @Transactional
    public void save(Model model) {}

    // 수정
    @Transactional
    public void update(Model model) {}
}
