package com.pot.app.infoarchive.endpoint.comment;

import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.article.dto.ArticleCreate;
import com.pot.app.infoarchive.domain.article.dto.ArticleDto;
import com.pot.app.infoarchive.domain.article.dto.ArticleUpdate;
import com.pot.app.infoarchive.domain.comment.dto.CommentCreate;
import com.pot.app.infoarchive.domain.comment.dto.CommentDto;
import com.pot.app.infoarchive.domain.comment.dto.CommentUpdate;
import com.pot.app.infoarchive.repository.comment.dao.CommentDao;
import com.pot.app.infoarchive.service.article.ArticleService;
import com.pot.app.infoarchive.service.comment.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public CommentDto create(@RequestBody CommentCreate request) {
        return service.save(request);
    }

    @GetMapping("/get")
    public CommentDto getArticle(@RequestBody IdDto request) {
        return service.getById(request);
    }

    @GetMapping("/get/all")
    public List<CommentDto> getArticle() {
        return service.getAll();
    }

    @PutMapping("/update")
    public CommentDto put(@RequestBody CommentUpdate request) {
        return service.update(request);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody IdDto request) {
        service.deleteById(request);
    }
}
