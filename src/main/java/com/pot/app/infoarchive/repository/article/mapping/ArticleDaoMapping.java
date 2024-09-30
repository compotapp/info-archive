package com.pot.app.infoarchive.repository.article.mapping;

import com.pot.app.infoarchive.domain.article.Article;
import com.pot.app.infoarchive.domain.comment.Comment;
import com.pot.app.infoarchive.repository.article.dao.ArticleDao;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.pot.app.infoarchive.repository.extension.GenerateId.generateArticleId;

public class ArticleDaoMapping {

    public static ArticleDao toDao(Article article) {
        var id = (article.getId() == null) ? generateArticleId() : article.getId();
        return new ArticleDao(
                id,
                article.getName(),
                article.getTags()
        );
    }

    public static Article toEntity(ArticleDao dao, List<Comment> comments) {
        return new Article(
                dao.getId(),
                dao.getName(),
                dao.getTags(),
                comments
        );
    }
}
