package com.example.firstproject.entity;

import com.example.firstproject.dto.PartDto;
import com.example.firstproject.dto.RegisterDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Part {


//    @GeneratedValue // 1, 2, 3 자동 생성 어노테이션
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략
    @Id // 대표값을 지정 like a 주민등록번호
    @Column(name = "part_code", length = 50) // 길이를 50으로 지정
    private String part_code;

    @Column(name = "part_name", length = 100, nullable = false)
    private String part_name;

    @Column(name = "part_std", length = 100, nullable = false)
    private String part_std;

    private UUID skey;

    // 회원가입
    public static Part createPart(PartDto dto) {
        // 예외 발생
        // 엔티티 생성 및 반환
        return new Part(
                dto.getPart_code(),
                dto.getPart_name(),
                dto.getPart_std(),
                dto.getSkey()
        );
    }

    @PrePersist
    public void generateUUID() {
        if (this.skey == null) {
            this.skey = UUID.randomUUID(); // UUID 생성
        }
    }

}
