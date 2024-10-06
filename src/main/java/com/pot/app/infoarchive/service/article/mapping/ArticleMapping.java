package com.pot.app.infoarchive.service.article.mapping;

import com.pot.app.infoarchive.domain.article.Article;
import com.pot.app.infoarchive.domain.article.dto.ArticleCreateRequest;
import com.pot.app.infoarchive.domain.article.dto.ArticleResponse;
import com.pot.app.infoarchive.domain.article.dto.ArticleUpdateRequest;
import com.pot.app.infoarchive.domain.comment.Comment;
import com.pot.app.infoarchive.service.comment.mapping.CommentMapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.pot.app.infoarchive.repository.extension.GenerateId.generateArticleId;

public class ArticleMapping {

    public static Article toEntity(ArticleResponse dto) {
        return new Article(
                new Article.ArticleId(UUID.fromString(dto.getArticleId())),
                dto.getName(),
                dto.getTags(),
                CommentMapping.toEntity(dto.getComments())
        );
    }

    public static Article toEntity(ArticleCreateRequest dto) {
        return new Article(
                dto.getName(),
                dto.getTags()
        );
    }

    public static Article toEntity(ArticleUpdateRequest dto) {
        return new Article(
                new Article.ArticleId(UUID.fromString(dto.getArticleId())),
                dto.getName(),
                dto.getTags()
        );
    }

    public static ArticleResponse toDto(Article article) {
        return new ArticleResponse(
                article.getArticleId().toString(),
                article.getName(),
                article.getTags(),
                CommentMapping.toDto(article.getComments())
        );
    }

    public static List<ArticleResponse> toDto(List<Article> articles) {
        return articles
                .stream()
                .map(ArticleMapping::toDto)
                .collect(Collectors.toList());
    }

    public static ArticleUpdateRequest toDao(Article article) {
        final Article.ArticleId id = (article.getArticleId() == null) ? generateArticleId() : article.getArticleId();
        return new ArticleUpdateRequest(
                id.toString(),
                article.getName(),
                article.getTags()
        );
    }

    public static Article toEntity(ArticleUpdateRequest dao, List<Comment> comments) {
        return new Article(
                new Article.ArticleId(UUID.fromString(dao.getArticleId())),
                dao.getName(),
                dao.getTags(),
                comments
        );
    }
}
