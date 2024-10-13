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
        return "login/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "login/register";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto dto, HttpSession session) {
        List<LoginDto> loginDtos = loginService.logins(dto);

        if(loginDtos != null && !loginDtos.isEmpty()) {
            log.info("로그인 성공");
            session.setAttribute("loginUserid", loginDtos.get(0).getUserid());
            log.info("Session ID: " + session.getId());
            log.info("Session Value: " + session.getAttribute("loginUserid"));
            return "redirect:/articles";
        }else {
            log.info("로그인 실패");
            return "login/login";
        }

        // return ResponseEntity.ok(loginDtos);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
