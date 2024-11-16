package com.example.firstproject.api;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
