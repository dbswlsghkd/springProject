package com.example.firstproject.entity;

import com.example.firstproject.dto.PartDto;
import com.example.firstproject.dto.PartnersDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Collection;

@Entity
@Table(name = "partner")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Partners {

    @Id // 대표값을 지정 like a 주민등록번호
    @Column(name = "partner_code", length = 5, columnDefinition = "CHAR(5)") // 길이를 50으로 지정
    private String partner_code;

    @Column(name = "partner_name", length =50, nullable = false)
    private String partner_name;

    @Column(name = "partner_address", length = 200, nullable = false)
    private String partner_address;

    // 거래처 등록
    public static Partners createPart(PartnersDto dto) {
        // 예외 발생
        // 엔티티 생성 및 반환
        return new Partners(
                dto.getPartner_code(),
                dto.getPartner_name(),
                dto.getPartner_address()
        );
    }

    public void patch(PartnersDto dto) {
        // 예외 발생
        // System.out.println("this =====> " + this.part_code + " dto =====>" + dto.getPart_code());

        if (!this.partner_code.equals(dto.getPartner_code()))
            throw new IllegalArgumentException("거래처 수정 실패! 잘못된 거래처가 입력되었습니다.");
        // 객체를 갱신
        if (dto.getPartner_name() != null)
            this.partner_name = dto.getPartner_name();
        if (dto.getPartner_address() != null)
            this.partner_address = dto.getPartner_address();
    }

}
