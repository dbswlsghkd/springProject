package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.util.*;

@Controller
@Slf4j
public class ArticleController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 자동 연결
    // private ArticleRepository articleRepository = new ArticleRepository(); 생략 가능
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public ResponseEntity<Article> createArticle(@RequestBody ArticleForm form) {
        log.info(form.toString());
        //System.out.println(form.toString()); -> 로깅기능으로 대체
        // 1. Dto 변환
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article);
        // 2. Repository에게 Entity를 Db에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
//        System.out.println(saved.toString());
        // 3. ResponseEntity로 반환 (201 Created)
        return ResponseEntity.created(URI.create("/articles/" + saved.getId())).body(saved);
    }

    // @GetMapping("/articles/{id}") // 해당 URL요청을 처리 선언
    // public String show(@PathVariable Long id,
    //                    Model model) { // URL에서 id를 변수로 가져옴
    //     log.info("id = " + id);
    //     // 1: id로 데이터를 가져옴!
    //     Article articleEntity = articleRepository.findById(id).orElse(null);
    //     // 2: 가져온 데이터를 모델에 등록!
    //     model.addAttribute("article", articleEntity);
    //     // 3: 보여줄 페이지를 설정!
    //     return "articles/show";
    // }
    @GetMapping("/articles/{id}") // 해당 URL요청을 처리 선언
    public ResponseEntity<Map<String, Object>> show(@PathVariable Long id) {
        log.info("id = " + id);

        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);

        Map<String, Object> response = new HashMap<>();
        response.put("article", articleEntity);
        response.put("comments", commentsDtos);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles")
    public String index(Model model, HttpSession session) {
        String loginUserid = (String) session.getAttribute("loginUserid");
        // 1: 모든 Article을 가져온다!
        List<Article> articleEntityList = articleRepository.findAll();
        // 2: 가져온 Article 묶음을 뷰로 전달!
        model.addAttribute("articleList", articleEntityList);
        model.addAttribute("loginUserid", loginUserid);
        // 3: 뷰 페이지를 설정!
        if (loginUserid != null) {
            log.info("세션 유지됨: " + loginUserid);
        } else {
            log.info("세션이 유지되지 않음");
        }

        String sessid = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("sessid : " + sessid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        log.info("role : " + role);

        model.addAttribute("sessid", sessid);
        model.addAttribute("role", role);

        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public ResponseEntity<Article> edit(@PathVariable Long id) {
        // 아티클을 ID로 찾기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        return ResponseEntity.ok(articleEntity); // 아티클 데이터를 JSON 형태로 반환
    }

    @PostMapping("/articles/update")
    public ResponseEntity<String> update(@RequestBody ArticleForm form) {

        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        try {
            // articleDto를 이용하여 데이터 업데이트 처리
            Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
            if (target != null) {
                target.setTitle(articleEntity.getTitle());
                target.setContent(articleEntity.getContent());
                articleRepository.save(target);
            }
            return ResponseEntity.ok("Article updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update article");
        }
    }

    @DeleteMapping("/articles/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("삭제 요청이 들어왔습니다!!");
        Article target = articleRepository.findById(id).orElse(null);
        if (target != null) {
            articleRepository.delete(target);
        }
        return ResponseEntity.ok().build(); // 성공 응답 반환
    }




}
