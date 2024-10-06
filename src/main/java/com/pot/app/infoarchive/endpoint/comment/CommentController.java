package com.pot.app.infoarchive.endpoint.comment;

import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.comment.dto.CommentCreateRequest;
import com.pot.app.infoarchive.domain.comment.dto.CommentResponse;
import com.pot.app.infoarchive.domain.comment.dto.CommentUpdateRequest;
import com.pot.app.infoarchive.service.comment.CommentService;
import jakarta.validation.Valid;
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
    public CommentResponse create(@Valid @RequestBody CommentCreateRequest request) {
        return service.save(request);
    }

    @PostMapping("/get")
    public CommentResponse getArticle(@Valid @RequestBody IdDto request) {
        return service.getById(request);
    }

    @GetMapping("/get/all")
    public List<CommentResponse> getArticle() {
        return service.getAll();
    }

    @PutMapping("/update")
    public CommentResponse put(@Valid @RequestBody CommentUpdateRequest request) {
        return service.update(request);
    }

    @PostMapping("/delete")
    public void delete(@Valid @RequestBody IdDto request) {
        service.deleteById(request);
    }
}
