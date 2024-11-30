package com.example.firstproject.entity;

import com.example.firstproject.dto.MoldDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Mold {
    @Id
    @Column(name = "m_pcode", length = 5, columnDefinition = "CHAR(6)")
    private String m_pcode;
    private String m_part_code;
    private BigDecimal m_cavity;

    public static Mold createMold(MoldDto dto){
        return new Mold(
                dto.getM_pcode(),
                dto.getM_part_code(),
                dto.getM_cavity()
        );
    }

    public void patch(MoldDto dto){

        if (!this.m_pcode.equals(dto.getM_pcode()))
            throw new IllegalArgumentException("금형 수정 실패! 잘못된 금형이 입력되었습니다.");
        // 객체를 갱신
        if (dto.getM_part_code() != null)
            this.m_part_code = dto.getM_part_code();
        if (dto.getM_cavity() != null)
            this.m_cavity = dto.getM_cavity();
    }
}
