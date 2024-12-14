package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
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

    @Column(name = "part_skey")
    private UUID part_skey;

    // 회원가입
    public static Part createPart(PartDto dto) {
        // 예외 발생
        // 엔티티 생성 및 반환
        return new Part(
                dto.getPart_code(),
                dto.getPart_name(),
                dto.getPart_std(),
                dto.getPart_skey()
        );
    }

    public void patch(PartDto dto) {
        // 예외 발생
        System.out.println("this =====> " + this.part_code + " dto =====>" + dto.getPart_code());

        if (!this.part_code.equals(dto.getPart_code()))
            throw new IllegalArgumentException("품번 수정 실패! 잘못된 품번이 입력되었습니다.");
        // 객체를 갱신
        if (dto.getPart_name() != null)
            this.part_name = dto.getPart_name();
        if (dto.getPart_std() != null)
            this.part_std = dto.getPart_std();
    }

    @PrePersist
    public void generateUUID() {
        if (this.part_skey == null) {
            this.part_skey = UUID.randomUUID(); // UUID 생성
        }
    }

}
