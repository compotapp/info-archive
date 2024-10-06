package com.pot.app.infoarchive.repository.extension;

import com.pot.app.infoarchive.domain.article.Article;
import com.pot.app.infoarchive.domain.comment.Comment;

import java.util.UUID;

public class GenerateId {

    public static Article.ArticleId generateArticleId() {
        return new Article.ArticleId(UUID.randomUUID());
    }

    public static Comment.CommentId generateCommentId() {
        final Comment comment = new Comment();
        return new Comment.CommentId(UUID.randomUUID());
    }
}
