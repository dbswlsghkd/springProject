package com.example.firstproject.service;

import com.example.firstproject.dto.PartDto;
import com.example.firstproject.dto.PartnersDto;
import com.example.firstproject.entity.Part;
import com.example.firstproject.entity.Partners;
import com.example.firstproject.mapper.PartnerMapper;
import com.example.firstproject.repository.PartRepository;
import com.example.firstproject.repository.PartnersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartnersService {

    @Autowired
    private PartnersRepository partnersRepository;

    private final PartnerMapper partnerMapper;

    public Page<Partners> index(Pageable pageable) {
        return partnerMapper.findByPartners(pageable);
    }

    public Page<Partners> searchParts(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        return partnerMapper.findBySearch(searchPattern, pageable);
    }

    public String getMaxPartnerCode() {
        return partnersRepository.findMaxPartnerCode();
    }

    @Transactional
    public PartnersDto create(PartnersDto dto) {
        // 품번 조회 및 예외 발생
        Partners partners = partnerMapper.findByPartnersCode(dto.getPartner_code());

        // 로그인 실패 시
        if (partners == null) {
            // 회원가입 엔티티 생성
            Partners partner = Partners.createPart(dto);
            log.info("partner : " + partner);
            // 회원가입 엔티티를 DB로 저장
            Partners created = partnersRepository.save(partner);
            log.info("created : " + created);
            // DTO로 변경하여 반환
            return PartnersDto.createPartnersDto(created);
        }else {
            log.info("거래처가 존재합니다.");
            return null;
        }
    }

    @Transactional
    public PartnersDto update(String partner_code, PartnersDto dto) {
        // 댓글 조회 및 예외 발생
        Partners target = partnersRepository.findPartnersCode(partner_code)
                .orElseThrow(() -> new IllegalArgumentException("거래처 수정 실패! 대상 거래처가 없습니다."));
        // 댓글 수정
        target.patch(dto);
        // DB로 갱신
        Partners updated = partnersRepository.save(target);
        // 댓글 엔티티를 DTO로 변환 및 반환
        return PartnersDto.createPartnersDto(updated);
    }
}
