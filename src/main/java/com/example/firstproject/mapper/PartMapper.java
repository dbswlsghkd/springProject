package com.example.firstproject.mapper;

import com.example.firstproject.entity.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PartMapper {

    private final SqlSessionTemplate sqlsessionTemplate;

    public Page<Part> findBySearch(String searchTerm, Pageable pageable) {
        // log.info("안에 들어와요?");
        // 페이징 파라미터 생성
        Map<String, Object> params = new HashMap<>();
        params.put("searchTerm", "%" + searchTerm + "%");
        params.put("offset", pageable.getPageNumber() * pageable.getPageSize());
        params.put("pageSize", pageable.getPageSize());

        log.info("findBySearch 쿼리 실행 전에 도달했습니다");
        // 데이터 조회
        List<Part> parts = sqlsessionTemplate.selectList("com.example.firstproject.mapper.UserMapper.findBySearch", params);
        log.info("findBySearch 쿼리 실행 후 결과: {}", parts);;
        // 전체 개수 조회
        int total = sqlsessionTemplate.selectOne("com.example.firstproject.mapper.UserMapper.countPartsSearch", params);

        // Page 객체로 변환
        return new PageImpl<>(parts, pageable, total);
    }

    // 검색어로 Part 조회
    // public List<Part> findBySearch(String searchTerm, Pageable pageable) {
    //     Map<String, Object> params = new HashMap<>();
    //     params.put("searchTerm", "%" + searchTerm + "%");
    //     params.put("offset", pageable.getPageNumber() * pageable.getPageSize());
    //     params.put("pageSize", pageable.getPageSize());
    //
    //     return sql.selectList("com.example.firstproject.mapper.PartMapper.findBySearch", params);
    // }

    // 전체 Part 조회
    public Page<Part> findPartBy(Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", pageable.getPageNumber() * pageable.getPageSize());
        params.put("pageSize", pageable.getPageSize());
        // 데이터 조회
        List<Part> parts = sqlsessionTemplate.selectList("com.example.firstproject.mapper.UserMapper.findPartBy", params);
        log.info("findPartBy 쿼리 실행 후 결과: {}", parts);;
        // 전체 개수 조회
        int total = sqlsessionTemplate.selectOne("com.example.firstproject.mapper.UserMapper.countParts", params);

        // Page 객체로 변환
        return new PageImpl<>(parts, pageable, total);
    }


    // partCode로 Part 조회
    public Part findByPartCode(String partCode) {
        // partCode로 조회하는 쿼리 실행
        return sqlsessionTemplate.selectOne("com.example.firstproject.mapper.UserMapper.findByPartCode", partCode);
    }

}
