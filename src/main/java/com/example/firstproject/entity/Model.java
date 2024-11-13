package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Model {

    @Id
    @Column(name = "model_code", length = 5, columnDefinition = "CHAR(5)") // 길이를 50으로 지정
    private String model_code;
    private String model_name;
    private LocalDateTime regdt;

}
