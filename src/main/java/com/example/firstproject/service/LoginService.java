package com.example.firstproject.service;

import com.example.firstproject.entity.Users;
import com.example.firstproject.dto.LoginDto;
import com.example.firstproject.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public List<LoginDto> logins(String userid) {
        // 반환
        return loginRepository.findByUserId(userid)
                .stream()
                .map(logins -> LoginDto.createLoginDto(logins))
                .collect(Collectors.toList());
    }

}
