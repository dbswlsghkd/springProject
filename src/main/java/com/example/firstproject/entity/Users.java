package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id // 대표값을 지정 like a 주민등록번호
    @Column(name = "userid", length = 50) // 길이를 50으로 지정
    private String userid;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "psword", length = 30, nullable = false)
    private String psword;

    @Column
    private LocalDateTime in_date;

    // 엔티티가 처음 저장되기 전에 regdt에 현재 시각을 자동으로 설정
    @PrePersist
    public void prePersist() {
        this.in_date = this.in_date == null ? LocalDateTime.now() : this.in_date;
    }
}
