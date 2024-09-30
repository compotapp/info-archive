package com.pot.app.infoarchive.endpoint.article;

import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.article.dto.ArticleCreate;
import com.pot.app.infoarchive.domain.article.dto.ArticleDto;
import com.pot.app.infoarchive.domain.article.dto.ArticleUpdate;
import com.pot.app.infoarchive.service.article.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ArticleDto create(@RequestBody ArticleCreate request) {
        return service.save(request);
    }

    @GetMapping("/get")
    public ArticleDto getArticle(@RequestBody IdDto request) {
        return service.getById(request);
    }

    @GetMapping("/get/all")
    public List<ArticleDto> getArticle() {
        return service.getAll();
    }

    @PutMapping("/update")
    public ArticleDto put(@RequestBody ArticleUpdate request) {
        return service.update(request);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody IdDto request) {
        service.deleteById(request);
    }
}
