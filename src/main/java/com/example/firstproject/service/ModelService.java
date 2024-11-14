package com.example.firstproject.service;

import com.example.firstproject.dto.ModelDto;
import com.example.firstproject.dto.PartDto;
import com.example.firstproject.entity.Model;
import com.example.firstproject.entity.Part;
import com.example.firstproject.entity.Partners;
import com.example.firstproject.repository.ModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    public Page<Model> index(Pageable pageable) {
        return modelRepository.findModelBy(pageable);
    }

    public Page<Model> searchModel(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        return modelRepository.findBySearch(searchPattern, pageable);
    }

    // 등록
    @Transactional
    public ModelDto create(ModelDto dto) {

        log.info("ModelDto" + dto.toString());
        // entity에서 id 찾기
        Model model = modelRepository.findByModelCode(dto.getModel_code());
        if(model == null) {
            // 모델 등록 엔티티 생성
            Model createModel = Model.createModel(dto);
            log.info("createModel ========> " + createModel);
            // 모델 등록 엔티티를 DB로 저장
            Model created = modelRepository.save(createModel);
            log.info("created : " + created);
            // DTO로 변경하여 반환
            return ModelDto.createModelDto(created);
        }else {
            log.info("모델이 존재합니다.");
            return null;
        }
    }

    // 수정
    @Transactional
    public void update(ModelDto dto) {}
}
