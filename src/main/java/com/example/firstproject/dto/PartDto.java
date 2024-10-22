package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Part;
import com.example.firstproject.entity.Users;
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

public class PartDto {

    private String part_code;
    private String part_name;
    private String part_std;
    private UUID skey;

    public static PartDto createPartDto(Part part) {
        return new PartDto(
                part.getPart_code(),
                part.getPart_name(),
                part.getPart_std(),
                part.getSkey()
        );
    }
}