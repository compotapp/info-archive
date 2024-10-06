package com.pot.app.infoarchive.service.comment;

import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.comment.dto.CommentCreateRequest;
import com.pot.app.infoarchive.domain.comment.dto.CommentResponse;
import com.pot.app.infoarchive.domain.comment.dto.CommentUpdateRequest;
import com.pot.app.infoarchive.repository.comment.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pot.app.infoarchive.service.comment.mapping.CommentMapping.toDto;
import static com.pot.app.infoarchive.service.comment.mapping.CommentMapping.toEntity;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public CommentResponse save(CommentCreateRequest request) {
        return toDto(repository.save(toEntity(request)));
    }

    @Override
    public CommentResponse getById(IdDto request) {
        return toDto(repository.getById(request.getId()));
    }

    @Override
    public List<CommentResponse> getAll() {
        return toDto(repository.getAll());
    }

    @Override
    public CommentResponse update(CommentUpdateRequest request) {
        return toDto(repository.update(toEntity(request)));
    }

    @Override
    public void deleteById(IdDto request) {
        repository.deleteById(request.getId());
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
