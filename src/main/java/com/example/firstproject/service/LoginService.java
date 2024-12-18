package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.entity.Users;
import com.example.firstproject.dto.LoginDto;
import com.example.firstproject.repository.LoginRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<LoginDto> logins(LoginDto loginDto) {
        // UserId로 조회하여 값이 없을 경우 예외 처리
        List<Users> loginList = loginRepository.findByUserId(loginDto.getUserid());
        log.info("loginList: {}", loginList);

        // 로그인 실패 시
        if (loginList.isEmpty()) {
            throw new EntityNotFoundException("User not found with ID: " + loginDto.getUserid());
        }else {
            Users user = loginList.get(0);
            if (passwordEncoder.matches(loginDto.getPsword(), user.getPsword())) {
                // 비밀번호 일치
                log.info(user.getUserid() + " getUserid");
                log.info(user.getPsword() + " getPsword");
                return convertToDtoList(loginList);
            }else {
                // 비밀번호 불일치
                log.info(user.getPsword() + " 비밀번호 일치하지 않습니다.");
                return null;
            }

        }
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