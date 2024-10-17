package com.example.firstproject.entity;

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

}
