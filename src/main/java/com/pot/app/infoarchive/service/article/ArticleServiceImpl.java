package com.pot.app.infoarchive.service.article;

import com.pot.app.infoarchive.domain.article.dto.ArticleCreateRequest;
import com.pot.app.infoarchive.domain.article.dto.ArticleResponse;
import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.article.dto.ArticleUpdateRequest;
import com.pot.app.infoarchive.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pot.app.infoarchive.service.article.mapping.ArticleMapping.toDto;
import static com.pot.app.infoarchive.service.article.mapping.ArticleMapping.toEntity;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repository;

    public ArticleServiceImpl(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArticleResponse save(ArticleCreateRequest request) {
        return toDto(repository.save(toEntity(request)));
    }

    @Override
    public ArticleResponse getById(IdDto request) {
        return toDto(repository.getById(request.getId()));
    }

    @Override
    public List<ArticleResponse> getAll() {
        return toDto(repository.getAll());
    }

    @Override
    public ArticleResponse update(ArticleUpdateRequest request) {
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
