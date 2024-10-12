package com.example.firstproject.controller;

import com.example.firstproject.dto.LoginDto;
import com.example.firstproject.entity.Part;
import com.example.firstproject.service.LoginService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;


    @GetMapping("/")
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

    @PostMapping("/login")
    public String login(@RequestBody LoginDto dto, HttpSession session) {
        List<LoginDto> loginDtos = loginService.logins(dto);

        if(loginDtos != null && !loginDtos.isEmpty()) {
            log.info("로그인 성공");
            log.info(loginDtos + "loginDtos");
            session.setAttribute("loginUserid", loginDtos.get(0).getUserid());
            log.info(loginDtos.get(0).getUserid() + "loginDtos.get(0).getUserid()");
            return "redirect:/articles";
        }else {
            log.info("로그인 실패");
            log.info(loginDtos + "loginDtos");
            return "login/login";
        }

        // return ResponseEntity.ok(loginDtos);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
