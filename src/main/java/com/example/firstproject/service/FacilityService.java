package com.example.firstproject.service;

import com.example.firstproject.entity.Facility;
import com.example.firstproject.repository.FacilityRepository;
import com.example.firstproject.repository.ModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    public Page<Facility> index(Pageable pageable) {
        return facilityRepository.findByFacility(pageable);
    }

    public Page<Facility> searchFacility(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        return facilityRepository.findBySearch(searchPattern, pageable);
    }
}
