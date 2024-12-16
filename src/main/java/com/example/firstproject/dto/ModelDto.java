package com.example.firstproject.dto;

import com.example.firstproject.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@AllArgsConstructor
//인자가 없는 기본 생성자를 자동으로 생성합니다.
@NoArgsConstructor
@Getter
@ToString
@Slf4j
public class ModelDto {

    private String model_code;
    private String model_name;
    private LocalDateTime model_regdt;

    public static ModelDto createModelDto(Model model) {
        return new ModelDto(
                model.getModel_code(),
                model.getModel_name(),
                model.getModel_regdt()
        );
    }

}
