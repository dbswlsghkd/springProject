package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;


@AllArgsConstructor
@ToString
public class ArticleForm {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime regdt;

    public Article toEntity() {
        return new Article(id, title, content, regdt);
    }
}
