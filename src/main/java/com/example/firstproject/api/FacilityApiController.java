package com.example.firstproject.api;

import com.example.firstproject.dto.FacilityDto;
import com.example.firstproject.entity.Facility;
import com.example.firstproject.service.FacilityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class FacilityApiController {

    @Autowired
    private FacilityService facilityService;

    @GetMapping("/api/facility")
    public Page<Facility> index(@RequestParam(value = "search", required = false) String searchTerm,
                                @RequestParam(value = "page") int page,  // 페이지 번호 (0부터 시작)
                                @RequestParam(value = "size") int size   // 페이지당 항목 수
    ){
        Pageable pageable = PageRequest.of(page, size);  // Pageable 객체 생성

        if (searchTerm != null && !searchTerm.isEmpty()) {
            return facilityService.searchFacility(searchTerm, pageable);
        }
        return facilityService.index(pageable);
    }

    @PostMapping("/api/facility/create")
    public ResponseEntity<FacilityDto> create(@RequestBody FacilityDto dto){

        log.info(dto.toString());

        FacilityDto createDto = facilityService.create(dto);

        return (createDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/facility/update/{facility_code}")
    public ResponseEntity<FacilityDto> update(@PathVariable String facility_code,@RequestBody FacilityDto Dto){

        FacilityDto updateDto = facilityService.update(facility_code, Dto);

        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
