package com.example.firstproject.controller;

import com.example.firstproject.entity.Partners;
import com.example.firstproject.repository.PartRepository;
import com.example.firstproject.repository.PartnersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class PartnersController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 자동 연결
    // private ArticleRepository articleRepository = new ArticleRepository(); 생략 가능
    private PartnersRepository partnersRepository;

    @GetMapping("/partners")
    public String index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {

        // 페이지 번호와 크기를 설정한 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);  // 0번째 페이지, 한 페이지에 10개 항목

        // 1: 모든 Article을 가져온다!
        Page<Partners> partnersEntityList = partnersRepository.findByPartners(pageable);
        log.info(partnersEntityList.toString() + " =>>>partners");
        // 2: 가져온 Article 묶음을 뷰로 전달!
        // 각 Part 엔티티의 part_std 값을 처리
        for (Partners partners : partnersEntityList) {
            // part_std 값이 null 또는 빈 문자열인 경우
            if (partners.getPartner_address() == null || partners.getPartner_address().isEmpty()) {
                partners.setPartner_address(""); // 기본값 설정
            }
        }
        // 페이징된 Part 데이터를 모델에 추가
        model.addAttribute("partners", partnersEntityList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", partnersEntityList.getTotalPages());
        // 3: 뷰 페이지를 설정!
        return "masterData/partner";
    }
}
