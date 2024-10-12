package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.dto.LoginDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
import com.example.firstproject.service.LoginService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @RequestMapping("/api/login")
@Slf4j

public class LoginApiController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/api/login")
    public String login(@RequestBody LoginDto dto) {
        List<LoginDto> loginDtos = loginService.logins(dto);

        if(loginDtos != null && !loginDtos.isEmpty()) {
            log.info("로그인 성공");
            log.info(loginDtos + "loginDtos");
            return "articles";
        }else {
            log.info("로그인 실패");
            log.info(loginDtos + "loginDtos");
            return "login";
        }


        // return ResponseEntity.ok(loginDtos);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // @GetMapping("/api/login/{userid}")
    // public ResponseEntity<List<LoginDto>> logins(@PathVariable String userid) {
        // 서비스에게 위임
        // List<LoginDto> dtos = loginService.logins(userid);
        // log.info("id = " + userid);
        //
        // log.info("dtos = " + dtos);
        // // 결과 응답
        // // return ResponseEntity.status(HttpStatus.OK).body(dtos);
        // return (dtos != null) ?
        //         ResponseEntity.status(HttpStatus.OK).body(dtos) :
        //         ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    // }

}
