package com.example.firstproject.repository;

import com.example.firstproject.entity.Part;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PartMapper {

    private final SqlSessionTemplate sql;

    // 검색어로 Part 조회
    public List<Part> findBySearch(String searchTerm, Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("searchTerm", "%" + searchTerm + "%");
        params.put("offset", pageable.getPageNumber() * pageable.getPageSize());
        params.put("pageSize", pageable.getPageSize());

        return sql.selectList("com.example.firstproject.repository.PartMapper.findBySearch", params);
    }

    // 전체 Part 조회
    public List<Part> findPartBy(Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", pageable.getPageNumber() * pageable.getPageSize());
        params.put("pageSize", pageable.getPageSize());

        return sql.selectList("com.example.firstproject.repository.PartMapper.findPartBy", params);
    }

    // partCode로 Part 조회
    public Part findByPartCode(String partCode) {
        // partCode로 조회하는 쿼리 실행
        return sql.selectOne("com.example.firstproject.repository.PartMapper.findByPartCode", partCode);
    }

    // Optional로 partCode 조회
    public Optional<Part> findPartCode(String partCode) {
        // Optional로 조회하는 쿼리 실행
        return Optional.ofNullable(sql.selectOne("com.example.firstproject.repository.PartMapper.findPartCode", partCode));
    }
}
