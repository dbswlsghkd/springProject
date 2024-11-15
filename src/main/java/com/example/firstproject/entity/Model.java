package com.example.firstproject.entity;

import com.example.firstproject.dto.ModelDto;
import com.example.firstproject.dto.PartDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Model {

    @Id
    @Column(name = "model_code", length = 5, columnDefinition = "CHAR(5)") // 길이를 50으로 지정
    private String model_code;
    private String model_name;
    private LocalDateTime regdt;


    // 엔티티가 처음 저장되기 전에 regdt에 현재 시각을 자동으로 설정
    @PrePersist
    public void prePersist() {
        this.regdt = this.regdt == null ? LocalDateTime.now() : this.regdt;
    }

    // 모델 등록
    public static Model createModel(ModelDto dto) {
        // 예외 발생
        // 엔티티 생성 및 반환
        return new Model(
                dto.getModel_code(),
                dto.getModel_name(),
                dto.getRegdt()
        );
    }

    public void patch(ModelDto dto) {
        if (!this.model_code.equals(dto.getModel_code()))
            throw new IllegalArgumentException("모델 수정 실패! 잘못된 모델이 입력되었습니다.");
        // 객체를 갱신
        if (dto.getModel_name() != null)
            this.model_name = dto.getModel_name();
    }

}
