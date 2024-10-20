package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
    public String createArticle(ArticleForm form) {
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
        return "redirect:/articles/" + saved.getId();
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
    public String show(@PathVariable Long id,
                       Model model) { // URL에서 id를 변수로 가져옴
        log.info("id = " + id);
        // 1: id로 데이터를 가져옴!
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);
        // 2: 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentsDtos);
        // 3: 보여줄 페이지를 설정!
        return "articles/show";
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
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        // 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());
        // 1: DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        // 2: 엔티티를 DB로 저장
        // 2-1: DB에서 기존 데이터를 가져옴
        Article target = articleRepository.findById(articleEntity.getId())
                .orElse(null);
        // 2-2: 기존 데이터가 있다면, 값을 갱신
        log.info(target.toString()+"target");
        if (target != null) {
            // 기존 엔티티의 필드를 업데이트
            target.setTitle(articleEntity.getTitle()); // title 업데이트
            target.setContent(articleEntity.getContent()); // content 업데이트
            // regdt는 업데이트하지 않음

            // 업데이트된 엔티티 저장
            articleRepository.save(target);
        }

        // 3: 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");
        // 1: 삭제 대상을 가져옴
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2: 대상을 삭제
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }
        // 3: 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }




}
