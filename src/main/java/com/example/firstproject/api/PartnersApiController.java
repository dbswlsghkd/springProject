package com.example.firstproject.api;

import com.example.firstproject.dto.PartDto;
import com.example.firstproject.dto.PartnersDto;
import com.example.firstproject.entity.Part;
import com.example.firstproject.entity.Partners;
import com.example.firstproject.service.PartService;
import com.example.firstproject.service.PartnersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PartnersApiController {

    @Autowired
    private PartnersService partnersService;

    // GET API with pagination
    @GetMapping("/api/partners")
    public Page<Partners> index(
            @RequestParam(value = "search", required = false) String searchTerm,
            @RequestParam(value = "page") int page,  // 페이지 번호 (0부터 시작)
            @RequestParam(value = "size") int size   // 페이지당 항목 수
    ) {
        log.info("searchTerm : " + searchTerm);
        Pageable pageable = PageRequest.of(page, size);  // Pageable 객체 생성

        // 검색어가 있으면 검색된 결과를, 없으면 전체 목록을 페이징 처리
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return partnersService.searchParts(searchTerm, pageable); // 검색어에 맞는 Part를 페이징 처리
        }

        return partnersService.index(pageable); // 전체 Part를 페이징 처리
    }

    // 거래처 등록
    @PostMapping("/api/partners/create")
    public ResponseEntity<PartnersDto> create(@RequestBody PartnersDto dto) {
        // 서비스에게 위임
        PartnersDto createdDto = partnersService.create(dto);
        // 결과 응답
        return (createdDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
