package com.example.firstproject.api;

import com.example.firstproject.dto.ModelDto;
import com.example.firstproject.entity.Model;
import com.example.firstproject.service.ModelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return modelService.searchModel(searchTerm, pageable); // 검색어에 맞는 model을 페이징 처리
        }
        return modelService.index(pageable); // 전체 model를 페이징 처리
    }

    @PostMapping("/api/model/create")
    public ResponseEntity<ModelDto> create(@RequestBody ModelDto modelDto) {

        log.info(modelDto.toString());

        ModelDto createDto = modelService.create(modelDto);
        // 결과 응답
        return (createDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/model/update/{modelcode}")
    public ResponseEntity<ModelDto> update(@PathVariable String modelcode ,@RequestBody ModelDto modelDto){
        ModelDto updateDto = modelService.update(modelcode, modelDto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
