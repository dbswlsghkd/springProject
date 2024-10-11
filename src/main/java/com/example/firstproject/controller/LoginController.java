package com.example.firstproject.controller;

import com.example.firstproject.entity.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String index(Model model) {
        // 1: 모든 Article을 가져온다!
        // List<Part> partEntityList = partRepository.findAll();

        // 2: 가져온 Article 묶음을 뷰로 전달!
        // 각 Part 엔티티의 part_std 값을 처리
        // for (Part part : partEntityList) {
        //     // part_std 값이 null 또는 빈 문자열인 경우
        //     if (part.getPart_std() == null || part.getPart_std().isEmpty()) {
        //         part.setPart_std(""); // 기본값 설정
        //     }
        // }
        // model.addAttribute("part", partEntityList);
        // 3: 뷰 페이지를 설정!
        return "login/login";
    }
}
