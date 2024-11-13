package com.example.firstproject.api;

import com.example.firstproject.entity.Model;
import com.example.firstproject.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

public class ModelsApiController {

    @Autowired
    private ModelService modelService;

    @GetMapping("/api/model")
    public Page<Model> index(
            @RequestParam(value = "search", required = false) String searchTerm,
            @RequestParam(value = "page") int page,  // 페이지 번호 (0부터 시작)
            @RequestParam(value = "size") int size   // 페이지당 항목 수
    ) {
        log.info("searchTerm : " + searchTerm);
        Pageable pageable = PageRequest.of(page, size);  // Pageable 객체 생성

        // 검색어가 있으면 검색된 결과를, 없으면 전체 목록을 페이징 처리
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return modelService.searchParts(searchTerm, pageable); // 검색어에 맞는 model을 페이징 처리
        }
        return modelService.index(pageable); // 전체 model를 페이징 처리
    }
}
