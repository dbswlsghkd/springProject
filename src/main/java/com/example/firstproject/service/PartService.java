package com.example.firstproject.service;

import com.example.firstproject.dto.PartDto;
import com.example.firstproject.entity.Part;
import com.example.firstproject.mapper.PartMapper;
import com.example.firstproject.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartService {
    private final PartMapper partMapper;

    @Autowired
    private PartRepository partRepository;

    public Page<Part> index(Pageable pageable) {
        // return partRepository.findPartBy(pageable);
        log.info("index==========> 여기 들어와" );
        return partMapper.findPartBy(pageable);
    }


    public Page<Part> searchParts(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        // return partRepository.findBySearch(searchPattern, pageable);
        log.info("searchParts ==========> 여기 들어와" );
        return partMapper.findBySearch(searchPattern, pageable);
    }

    @Transactional
    public PartDto create(PartDto dto) {
        // 품번 조회 및 예외 발생
        // Part parts = partRepository.findByPartCode(dto.getPart_code());
        Part parts = partMapper.findByPartCode(dto.getPart_code());
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

    @Transactional
    public PartDto update(String partcode, PartDto dto) {
        // 댓글 조회 및 예외 발생
        // Part target = partRepository.findPartCode(partcode)
        Part target = partRepository.findByPartCode(partcode);
        log.info("target.toString() ========> " +target.toString());
        // 댓글 수정
        if(target == null) {
            return null;
        }else {
            target.patch(dto);
            // DB로 갱신
            Part updated = partRepository.save(target);
            // 댓글 엔티티를 DTO로 변환 및 반환
            return PartDto.createPartDto(updated);
        }
    }

}
