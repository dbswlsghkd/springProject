package com.example.firstproject.api;

import com.example.firstproject.dto.LoginDto;
import com.example.firstproject.dto.RegisterDto;
import com.example.firstproject.service.LoginService;
import com.example.firstproject.service.RegisterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    // 회원가입
    @PostMapping("/api/register")
    public ResponseEntity<RegisterDto> create(@RequestBody RegisterDto dto) {
        // 서비스에게 위임
        RegisterDto createdDto = registerService.create(dto);
        // 결과 응답
        return (createdDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
