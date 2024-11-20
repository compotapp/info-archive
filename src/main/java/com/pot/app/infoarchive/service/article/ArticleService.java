package com.pot.app.infoarchive.service.article;

import com.pot.app.infoarchive.domain.article.dto.ArticleCreateRequest;
import com.pot.app.infoarchive.domain.article.dto.ArticleResponse;
import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.article.dto.ArticleUpdateRequest;

import java.util.List;

public interface ArticleService {

    ArticleResponse save(ArticleCreateRequest request);

    ArticleResponse getById(IdDto request);

    List<ArticleResponse> getAll();

    ArticleResponse update(ArticleUpdateRequest request);

    void deleteById(IdDto request);

    void deleteAll();
}
