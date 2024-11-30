package com.example.firstproject.dto;

import com.example.firstproject.entity.Mold;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
//인자가 없는 기본 생성자를 자동으로 생성합니다.
@NoArgsConstructor
@Getter
@ToString
public class MoldDto {

    private String m_pcode;
    private String m_part_code;
    private BigDecimal m_cavity;

    public static MoldDto createMoldDto(Mold mold) {
        return new MoldDto(
                mold.getM_pcode(),
                mold.getM_part_code(),
                mold.getM_cavity()
        );
    }
}
