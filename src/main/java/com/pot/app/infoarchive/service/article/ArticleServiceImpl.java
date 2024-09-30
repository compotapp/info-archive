package com.pot.app.infoarchive.service.article;

import com.pot.app.infoarchive.domain.article.dto.ArticleCreate;
import com.pot.app.infoarchive.domain.article.dto.ArticleDto;
import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.article.dto.ArticleUpdate;
import com.pot.app.infoarchive.repository.article.ArticleRepository;
import com.pot.app.infoarchive.service.article.mapping.ArticleMapping;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.pot.app.infoarchive.service.article.mapping.ArticleMapping.toDto;
import static com.pot.app.infoarchive.service.article.mapping.ArticleMapping.toEntity;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repository;

    public ArticleServiceImpl(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArticleDto save(ArticleCreate request) {
        return toDto(repository.save(toEntity(request)));
    }

    @Override
    public ArticleDto getById(IdDto request) {
        return toDto(repository.getById(request.getId()));
    }

    @Override
    public List<ArticleDto> getAll() {
        return toDto(repository.getAll());
    }

    @Override
    public ArticleDto update(ArticleUpdate request) {
        return toDto(repository.update(toEntity(request)));
    }

    @Override
    public void deleteById(IdDto request) {
        repository.deleteById(request.getId());
    }
}
