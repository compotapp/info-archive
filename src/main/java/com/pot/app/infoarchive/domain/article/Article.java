package com.pot.app.infoarchive.domain.article;

import com.pot.app.infoarchive.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class Article{

    private ArticleId articleId;
    private String name;
    private Set<String> tags;
    private List<Comment> comments;

    public Article(String name, Set<String> tags) {
        this.name = name;
        this.tags = tags;
    }

    public Article(ArticleId articleId, String name, Set<String> tags) {
        this.articleId = articleId;
        this.name = name;
        this.tags = tags;
    }

    public Article setArticleId(ArticleId articleId) {
        return create(articleId, name, tags, comments);
    }

    public Article setName(String name) {
        return create(articleId, name, tags, comments);
    }

    public Article addTags(Set<String> tags) {
        this.tags.addAll(tags);
        return create(articleId, name, this.tags, comments);
    }

    public Article setTags(Set<String> tags) {
        return create(articleId, name, tags, comments);
    }

    public Article addComments(List<Comment> comments) {
        this.comments.addAll(comments);
        return create(articleId, name, tags, this.comments);
    }

    public Article setComments(List<Comment> comments) {
        return create(articleId, name, tags, comments);
    }

    private Article create(ArticleId id, String name, Set<String> tags, List<Comment> comments) {
        return new Article(id, name, tags, comments);
    }

    @AllArgsConstructor
    public static class ArticleId implements Serializable{

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
