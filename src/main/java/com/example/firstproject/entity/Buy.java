package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "buy") // 테이블 이름이 "buy"
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Buy {

    @Id
    @Column(name = "buy_bal_no", length = 14)
    private String buyBalNo;

}
