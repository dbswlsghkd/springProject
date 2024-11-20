package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Mold {
    @Id
    @Column(name = "m_pcode", length = 5, columnDefinition = "CHAR(6)")
    private String m_pcode;
    private String m_part_code;
    private BigDecimal m_cavity;
}
