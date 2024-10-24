package com.example.firstproject.dto;


import com.example.firstproject.entity.Part;
import com.example.firstproject.entity.Partners;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
//인자가 없는 기본 생성자를 자동으로 생성합니다.
@NoArgsConstructor
@Getter
@ToString
public class PartnersDto {

    private String partner_code;
    private String partner_name;
    private String partner_address;

    public static PartnersDto createPartnersDto(Partners partners) {
        return new PartnersDto(
                partners.getPartner_code(),
                partners.getPartner_name(),
                partners.getPartner_address()
        );
    }
}
