package com.example.firstproject.dto;

import com.example.firstproject.entity.Users;
import lombok.*;
//모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.
@AllArgsConstructor
//인자가 없는 기본 생성자를 자동으로 생성합니다.
@NoArgsConstructor
@Getter
@ToString
public class LoginDto {

    private String userid;
    private String name;
    private String psword;

    public static LoginDto createLoginDto(Users users) {
        return new LoginDto(
                users.getUserid(),
                users.getName(),
                users.getPsword()
        );
    }

}
