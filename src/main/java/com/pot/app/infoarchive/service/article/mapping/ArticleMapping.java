package com.pot.app.infoarchive.service.article.mapping;

import com.pot.app.infoarchive.domain.article.Article;
import com.pot.app.infoarchive.domain.article.dto.ArticleCreate;
import com.pot.app.infoarchive.domain.article.dto.ArticleDto;
import com.pot.app.infoarchive.domain.article.dto.ArticleUpdate;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleMapping {

    public static Article toEntity(ArticleDto dto) {
        return new Article(
                dto.getId(),
                dto.getName(),
                dto.getTags(),
                dto.getComments()
        );
    }

    public static Article toEntity(ArticleCreate dto) {
        return new Article(
                dto.getName(),
                dto.getTags()
        );
    }

    public static Article toEntity(ArticleUpdate dto) {
        return new Article(
                dto.getId(),
                dto.getName(),
                dto.getTags()
        );
    }

    public static ArticleDto toDto(Article article) {
        return new ArticleDto(
                article.getId(),
                article.getName(),
                article.getTags(),
                article.getComments()
        );
    }

    public static List<ArticleDto> toDto(List<Article> articles) {
        return articles
                .stream()
                .map(ArticleMapping::toDto)
                .collect(Collectors.toList());
    }
}
