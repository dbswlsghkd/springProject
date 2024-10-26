package com.example.firstproject.api;

import com.example.firstproject.entity.Part;
import com.example.firstproject.entity.Users;
import com.example.firstproject.repository.UsersRepository;
import com.example.firstproject.service.PartService;
import com.example.firstproject.service.UsersService;
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
public class UsersApiController {

    @Autowired
    private UsersService usersService;

    // GET API with pagination
    @GetMapping("/api/users")
    public Page<Users> index(
            @RequestParam(value = "search", required = false) String searchTerm,
            @RequestParam(value = "page") int page,  // 페이지 번호 (0부터 시작)
            @RequestParam(value = "size") int size   // 페이지당 항목 수
    ) {
        log.info("searchTerm : " + searchTerm);
        Pageable pageable = PageRequest.of(page, size);  // Pageable 객체 생성

        // 검색어가 있으면 검색된 결과를, 없으면 전체 목록을 페이징 처리
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return usersService.getAllUsers(searchTerm, pageable); // 검색어에 맞는 Users를 페이징 처리
        }

        return usersService.index(pageable); // 전체 Users를 페이징 처리
    }
}
