package com.pot.app.infoarchive.endpoint.article;

import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.article.dto.ArticleCreateRequest;
import com.pot.app.infoarchive.domain.article.dto.ArticleResponse;
import com.pot.app.infoarchive.domain.article.dto.ArticleUpdateRequest;
import com.pot.app.infoarchive.service.article.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService service;

    @PostMapping("/create")
    public ArticleResponse create(@Valid @RequestBody ArticleCreateRequest request) {
        return service.save(request);
    }

    @PostMapping("/get")
    public ArticleResponse getArticle(@Valid @RequestBody IdDto request) {
        return service.getById(request);
    }

    @GetMapping("/get/all")
    public List<ArticleResponse> getArticle() {
        return service.getAll();
    }

    @PutMapping("/update")
    public ArticleResponse put(@Valid @RequestBody ArticleUpdateRequest request) {
        return service.update(request);
    }

    @PostMapping("/delete")
    public void delete(@Valid @RequestBody IdDto request) {
        service.deleteById(request);
    }
}
