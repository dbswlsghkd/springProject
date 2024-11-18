package com.example.firstproject.service;

import com.example.firstproject.dto.FacilityDto;
import com.example.firstproject.entity.Facility;
import com.example.firstproject.repository.FacilityRepository;
import com.example.firstproject.repository.ModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public FacilityDto create(FacilityDto facilityDto) {
        Facility facility = facilityRepository.findByFacilityCode(facilityDto.getFacility_code());
        if(facility == null) {

            Facility facility1 = Facility.createFacility(facilityDto);
            log.info("facility1 : " + facility1);
            Facility  createdFacility = facilityRepository.save(facility1);
            log.info("createdFacility : " + createdFacility);
            return FacilityDto.createFacility(createdFacility);

        }else{
            log.info("설비가 존재합니다.");
            return null;
        }
    }

}
