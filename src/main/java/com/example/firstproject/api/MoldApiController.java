package com.example.firstproject.api;

import com.example.firstproject.entity.Mold;
import com.example.firstproject.service.MoldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
