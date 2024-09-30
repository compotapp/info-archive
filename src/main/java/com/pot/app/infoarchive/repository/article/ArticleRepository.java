package com.pot.app.infoarchive.repository.article;

import com.pot.app.infoarchive.domain.article.Article;

import java.util.List;

public interface ArticleRepository {

    Article save(Article article);

    Article getById(Long id);

    List<Article> getAll();

    Article update(Article article);

    void deleteById(Long id);
}
