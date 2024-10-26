package com.example.firstproject.controller;


import com.example.firstproject.entity.Users;
import com.example.firstproject.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/users")
    public String users(@RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "5") int size,
                        Model model) {
        // 페이지 번호와 크기를 설정한 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);  // 0번째 페이지, 한 페이지에 10개 항목

        // 1: 모든 Article을 가져온다!
        Page<Users> UsersEntityList = usersRepository.getAllUsers(pageable);

        log.info(UsersEntityList.toString() + "==============> UsersEntityList");
        // 2: 가져온 Article 묶음을 뷰로 전달!

        // 페이징된 Part 데이터를 모델에 추가
        model.addAttribute("Users", UsersEntityList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", UsersEntityList.getTotalPages());
        // 3: 뷰 페이지를 설정!

        return "masterData/users";
    }
}
