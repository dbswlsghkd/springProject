package com.example.firstproject.api;

import com.example.firstproject.entity.Part;
import com.example.firstproject.service.PartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class PartApiController {

    @Autowired
    private PartService partService;

    // GET 리팩토링
    @GetMapping("/api/parts")
    public List<Part> index() {
        return partService.index();
    }
}
