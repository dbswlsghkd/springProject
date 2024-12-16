package com.example.firstproject.mapper;

import com.example.firstproject.entity.Model;
import com.example.firstproject.entity.Mold;
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
public class ModelMapper {

    private final SqlSessionTemplate sqlSessionTemplate;

    public Page<Model> findBySearch(String searchTerm, Pageable pageable) {
        // log.info("안에 들어와요?");
        // 페이징 파라미터 생성
        Map<String, Object> params = new HashMap<>();
        params.put("searchTerm", "%" + searchTerm + "%");
        params.put("offset", pageable.getPageNumber() * pageable.getPageSize());
        params.put("pageSize", pageable.getPageSize());

        // 데이터 조회
        List<Model> models = sqlSessionTemplate.selectList("com.example.firstproject.mapper.ModelMapper.findBySearch", params);
        // 전체 개수 조회
        int total = sqlSessionTemplate.selectOne("com.example.firstproject.mapper.ModelMapper.countModelsSearch", params);

        // Page 객체로 변환
        return new PageImpl<>(models, pageable, total);
    }

    public Page<Model> findModelAll(Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", pageable.getPageNumber() * pageable.getPageSize());
        params.put("pageSize", pageable.getPageSize());

        log.info("findPartBy 쿼리 실행 전에 도달했습니다");
        // 데이터 조회
        List<Model> models = sqlSessionTemplate.selectList("com.example.firstproject.mapper.ModelMapper.findModelAll", params);
        log.info("findPartBy 쿼리 실행 후 결과: {}", models);
        // 전체 개수 조회
        int total = sqlSessionTemplate.selectOne("com.example.firstproject.mapper.ModelMapper.countModels", params);

        // Page 객체로 변환
        return new PageImpl<>(models, pageable, total);
    }

    public Model findByModelCode(String mCode) {
        return sqlSessionTemplate.selectOne("com.example.firstproject.mapper.ModelMapper.findByModelCode", mCode);
    }
}
