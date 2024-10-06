package com.pot.app.infoarchive.domain.comment;

import com.pot.app.infoarchive.domain.article.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class Comment{

    private CommentId commentId;
    private Article.ArticleId articleId;
    private String text;

    public Comment(String text) {
        this.text = text;
    }

    public Comment(CommentId commentId, String text) {
        this.commentId = commentId;
        this.text = text;
    }

    public Comment(Article.ArticleId articleId, String text) {
        this.articleId = articleId;
        this.text = text;
    }

    public Comment setCommentId(CommentId commentId) {
        return create(commentId, articleId, text);
    }

    public Comment setArticle(Article.ArticleId article) {
        return create(commentId, article, text);
    }

    public Comment setText(String text) {
        return create(commentId, articleId, text);
    }

    private Comment create(CommentId id, Article.ArticleId article, String text) {
        return new Comment(id, article, text);
    }

    @AllArgsConstructor
    public static class CommentId implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private UUID id;

        public UUID value() {
            return id;
        }

        @Override
        public String toString() {
            return id.toString();
        }
    }
}
