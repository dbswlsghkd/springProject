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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;


    // @GetMapping("/")
    // public String index(Model model) {
    //     return "login/login";
    // }

    @GetMapping("/register")
    public String register(Model model) {
        return "login/register";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login/login"; // login.mustache를 반환
    }

    // spring security 사용 하면 해당 부분은 필요가 없음
    // @PostMapping("/login")
    // public String login(@RequestBody LoginDto dto, HttpSession session) {
    //     List<LoginDto> loginDtos = loginService.logins(dto);

        // try {
        //     loginDtos = loginService.logins(dto);
        //     log.info("로그인 성공: -->" + loginDtos);
        // } catch (EntityNotFoundException e) {
        //     log.info("로그인 실패: " + e.getMessage());
        //     return "/"; // 로그인 실패 시 이동
        // }


        // return "/";
        // log.info("loginDtos ====>" + loginDtos);
        // if(loginDtos != null && !loginDtos.isEmpty()) {
        //     log.info("로그인 성공");
        //     session.setAttribute("loginUserid", loginDtos.get(0).getUserid());
        //     log.info("Session ID: " + session.getId());
        //     log.info("Session Value: " + session.getAttribute("loginUserid"));
        //     return ResponseEntity.ok("로그인 성공");
        // }else {
        //     log.info("로그인 실패");
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패"); // 로그인 실패 시 401 Unauthorized 응답
        // }

        // return ResponseEntity.ok(loginDtos);
    // }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
