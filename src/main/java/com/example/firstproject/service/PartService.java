package com.example.firstproject.service;

import com.example.firstproject.dto.PartDto;
import com.example.firstproject.dto.RegisterDto;
import com.example.firstproject.entity.Part;
import com.example.firstproject.entity.Users;
import com.example.firstproject.repository.PartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PartService {

    @Autowired
    private PartRepository partRepository;

    public Page<Part> index(Pageable pageable) {
        return partRepository.findPartBy(pageable);
    }

    public Page<Part> searchParts(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        return partRepository.findBySearch(searchPattern, pageable);
    }

    @Transactional
    public PartDto create(PartDto dto) {
        // 품번 조회 및 예외 발생
        Part parts = partRepository.findByPartCode(dto.getPart_code());
        // 로그인 실패 시
        if (parts == null) {
            // 회원가입 엔티티 생성
            Part part = Part.createPart(dto);
            log.info("part : " + part);
            // 회원가입 엔티티를 DB로 저장
            Part created = partRepository.save(part);
            log.info("created : " + created);
            // DTO로 변경하여 반환
            return PartDto.createPartDto(created);
        }else {
            log.info("품번이 존재합니다.");
            return null;
        }
    }

}
