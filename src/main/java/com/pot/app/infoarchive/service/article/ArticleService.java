package com.pot.app.infoarchive.service.article;

import com.pot.app.infoarchive.domain.article.dto.ArticleCreate;
import com.pot.app.infoarchive.domain.article.dto.ArticleDto;
import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.article.dto.ArticleUpdate;

import java.util.List;

public interface ArticleService {

    ArticleDto save(ArticleCreate request);

    ArticleDto getById(IdDto request);

    List<ArticleDto> getAll();

    ArticleDto update(ArticleUpdate request);

    void deleteById(IdDto request);
}
