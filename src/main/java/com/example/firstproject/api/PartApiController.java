package com.example.firstproject.api;

import com.example.firstproject.dto.PartDto;
import com.example.firstproject.dto.RegisterDto;
import com.example.firstproject.entity.Part;
import com.example.firstproject.service.PartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@Slf4j
public class PartApiController {

    @Autowired
    private PartService partService;

    // GET API with pagination
    @GetMapping("/api/parts")
    public Page<Part> index(
            @RequestParam(value = "search", required = false) String searchTerm,
            @RequestParam(value = "page") int page,  // 페이지 번호 (0부터 시작)
            @RequestParam(value = "size") int size   // 페이지당 항목 수
    ) {
        log.info("searchTerm : " + searchTerm);
        Pageable pageable = PageRequest.of(page, size);  // Pageable 객체 생성

        // 검색어가 있으면 검색된 결과를, 없으면 전체 목록을 페이징 처리
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return partService.searchParts(searchTerm, pageable); // 검색어에 맞는 Part를 페이징 처리
        }

        return partService.index(pageable); // 전체 Part를 페이징 처리
    }

    // 회원가입
    @PostMapping("/api/part/create")
    public ResponseEntity<PartDto> create(@RequestBody PartDto dto) {
        // 서비스에게 위임
        PartDto createdDto = partService.create(dto);
        // 결과 응답
        return (createdDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
