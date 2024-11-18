package com.example.firstproject.dto;

import com.example.firstproject.entity.Facility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
//인자가 없는 기본 생성자를 자동으로 생성합니다.
@NoArgsConstructor
@Getter
@ToString

public class FacilityDto {

    private String facility_code;
    private String facility_name;
    private LocalDateTime regdt;
    private UUID skey;

    public static FacilityDto createFacility(Facility facility) {
        return new FacilityDto(
                facility.getFacility_code(),
                facility.getFacility_name(),
                facility.getRegdt(),
                facility.getSkey()
        );
    }
}
