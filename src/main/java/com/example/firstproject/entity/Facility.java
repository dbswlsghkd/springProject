package com.example.firstproject.entity;

import com.example.firstproject.dto.FacilityDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Facility {

    @Id
    @Column(name = "facility_code", length = 6, columnDefinition = "CHAR(6)")
    private String facility_code;
    private String facility_name;
    private LocalDateTime regdt;
    private UUID skey;

    @PrePersist
    public void prePersist(){
        this.regdt = this.regdt == null ? LocalDateTime.now() : this.regdt;
        this.skey = this.skey == null ? UUID.randomUUID() : this.skey;
    }

    public static Facility createFacility(FacilityDto dto){
        return new Facility(
                dto.getFacility_code(),
                dto.getFacility_name(),
                dto.getRegdt(),
                dto.getSkey()
        );
    }

    public void patch(FacilityDto dto){
        if(!this.facility_code.equals(dto.getFacility_code()))
            throw new IllegalArgumentException("설비 수정 실패! 잘못된 설비가 입력되었습니다.");

        if (dto.getFacility_name() != null)
            this.facility_name = dto.getFacility_name();
    }
}
