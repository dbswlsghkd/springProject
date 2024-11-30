package com.example.firstproject.api;

import com.example.firstproject.dto.MoldDto;
import com.example.firstproject.entity.Mold;
import com.example.firstproject.service.MoldService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MoldApiController {

    @Autowired
    private MoldService moldService;

    @GetMapping("/api/mold")
    public Page<Mold> index(@RequestParam(value = "search", required = false) String searchTerm,
                                @RequestParam(value = "page") int page,  // 페이지 번호 (0부터 시작)
                                @RequestParam(value = "size") int size   // 페이지당 항목 수
    ){
        Pageable pageable = PageRequest.of(page, size);

        if (searchTerm != null && !searchTerm.isEmpty()) {
            return moldService.searchMold(searchTerm, pageable);
        }
        return moldService.index(pageable);
    }

    @PostMapping("api/mold/create")
    public ResponseEntity<MoldDto> create(@RequestBody MoldDto moldDto){
        MoldDto createDto = moldService.create(moldDto);

        return (createDto != null) ? ResponseEntity.ok(createDto) : ResponseEntity.notFound().build();

    }

    @PatchMapping("/api/mold/update/{m_pcode}")
    public ResponseEntity<MoldDto> update(@PathVariable String m_pcode,@RequestBody MoldDto moldDto){
        MoldDto updateDto = moldService.update(m_pcode, moldDto);

        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
