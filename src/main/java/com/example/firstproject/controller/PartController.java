package com.example.firstproject.controller;


import com.example.firstproject.entity.Part;
import com.example.firstproject.repository.PartRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
@Slf4j
public class PartController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 자동 연결
    // private ArticleRepository articleRepository = new ArticleRepository(); 생략 가능
    private PartRepository partRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/parts")
    public String index(Model model) {

        // 페이지 번호와 크기를 설정한 Pageable 객체 생성
        // Pageable pageable = PageRequest.of(0, 30);  // 0번째 페이지, 한 페이지에 10개 항목

        // 1: 모든 Article을 가져온다!
        List<Part> partEntityList = partRepository.findPartBy();

        // 2: 가져온 Article 묶음을 뷰로 전달!
        // 각 Part 엔티티의 part_std 값을 처리
        for (Part part : partEntityList) {
            // part_std 값이 null 또는 빈 문자열인 경우
            if (part.getPart_std() == null || part.getPart_std().isEmpty()) {
                part.setPart_std(""); // 기본값 설정
            }
        }
        model.addAttribute("part", partEntityList);
        // 3: 뷰 페이지를 설정!
        return "masterData/part";
    }

}
