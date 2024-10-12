package com.example.firstproject.service;

import com.example.firstproject.entity.Users;
import com.example.firstproject.dto.LoginDto;
import com.example.firstproject.repository.LoginRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public List<LoginDto> logins(String userid, String password) {
        // UserId로 조회하여 값이 없을 경우 예외 처리
        List<Users> loginList = loginRepository.findByUserIdAndPsword(userid, password);
        log.info(loginList.toString() + "loginList");

        if (loginList.isEmpty()) {
            throw new EntityNotFoundException("User not found with ID: " + userid);
        }

        // Dto 변환 처리
        return convertToDtoList(loginList);
    }

    // Dto로 변환하는 메서드 분리
    private List<LoginDto> convertToDtoList(List<Users> logins) {
        return logins.stream()
                .map(LoginDto::createLoginDto)
                .collect(Collectors.toList());
    }
        // 반환
        // return loginRepository.findByUserId(userid)
        //         .stream()
        //         .map(logins -> LoginDto.createLoginDto(logins))
        //         .collect(Collectors.toList());
    // }

}
