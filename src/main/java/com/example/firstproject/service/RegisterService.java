package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.dto.LoginDto;
import com.example.firstproject.dto.RegisterDto;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.entity.Users;
import com.example.firstproject.repository.LoginRepository;
import com.example.firstproject.repository.RegisterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Transactional
    public RegisterDto create(RegisterDto dto) {
        // 게시글 조회 및 예외 발생
        List<Users> users = registerRepository.findByUserId(dto.getUserid());
        // 로그인 실패 시
        if (users.isEmpty()) {
            // 회원가입 엔티티 생성
            Users comment = Users.createUsers(dto);
            // 회원가입 엔티티를 DB로 저장
            Users created = registerRepository.save(comment);
            // DTO로 변경하여 반환
            return RegisterDto.createLoginDto(created);
        }else {
            log.info("아이디가 존재합니다.");
            return null;
        }
    }
}
