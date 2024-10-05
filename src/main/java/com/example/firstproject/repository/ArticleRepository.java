package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

// spring에서 제공해주는 CrudRepository 기능 사용가능
// CRUD 기능 사용가능
public interface ArticleRepository extends CrudRepository<Article, Long> {

}
