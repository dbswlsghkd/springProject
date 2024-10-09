package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.*;

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

}
