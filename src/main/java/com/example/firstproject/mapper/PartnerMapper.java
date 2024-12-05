package com.example.firstproject.mapper;

import com.example.firstproject.entity.Part;
import com.example.firstproject.entity.Partners;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor

public class PartnerMapper {

    private final SqlSessionTemplate sqlsessionTemplate;

    public Page<Partners> findBySearch(String searchTerm, Pageable pageable){
        Map<String, Object> params = new HashMap<>();
        params.put("searchTerm", "%" + searchTerm + "%");
        params.put("offset", pageable.getPageNumber() * pageable.getPageSize());
        params.put("pageSize", pageable.getPageSize());

        List<Partners> partners = sqlsessionTemplate.selectList("com.example.firstproject.mapper.PartnerMapper.findBySearch",params);
        log.info("findPartBy 쿼리 실행 후 결과: {}", partners);;
        int total = sqlsessionTemplate.selectOne("com.example.firstproject.mapper.PartnerMapper.countByPartnersSearch",params);
        return new PageImpl<>(partners, pageable, total);
    }

    public Page<Partners> findByPartners(Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", pageable.getPageNumber() * pageable.getPageSize());
        params.put("pageSize", pageable.getPageSize());
        // 데이터 조회
        List<Partners> partners = sqlsessionTemplate.selectList("com.example.firstproject.mapper.PartnerMapper.findByPartners", params);
        log.info("findPartBy 쿼리 실행 후 결과: {}", partners);;
        // 전체 개수 조회
        int total = sqlsessionTemplate.selectOne("com.example.firstproject.mapper.PartnerMapper.countPartners", params);

        // Page 객체로 변환
        return new PageImpl<>(partners, pageable, total);
    }

    public Partners findByPartnersCode(String partnersCode) {

        return sqlsessionTemplate.selectOne("com.example.firstproject.mapper.PartnerMapper.findPartnersCode", partnersCode);
    }


}
