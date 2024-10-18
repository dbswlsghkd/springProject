package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
//인자가 없는 기본 생성자를 자동으로 생성합니다.
@NoArgsConstructor
@Getter
@ToString

public class RegisterDto {

    private String userid;
    private String name;
    private String psword;
    private LocalDateTime in_date;
    private String role;

    public static RegisterDto createRegisterDto(Users users) {
        return new RegisterDto(
                users.getUserid(),
                users.getName(),
                users.getPsword(),
                users.getIn_date(),
                users.getRole()
        );
    }
}
