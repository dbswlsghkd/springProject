package com.example.firstproject.service;

import com.example.firstproject.entity.Part;
import com.example.firstproject.repository.PartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PartService {

    @Autowired
    private PartRepository partRepository;

    public List<Part> index() {
        return partRepository.findPartBy();
    }

}
