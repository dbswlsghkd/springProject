package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.dto.LoginDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
import com.example.firstproject.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j

public class LoginApiController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/api/login/{userid}")
    public ResponseEntity<List<LoginDto>> logins(@PathVariable String userid) {
        // 서비스에게 위임
        List<LoginDto> dtos = loginService.logins(userid);
        log.info("id = " + userid);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

}
