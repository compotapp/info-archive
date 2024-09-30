package com.pot.app.infoarchive.domain.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class Comment{

    private Long id;
    private Long articleId;
    private String text;

    public Comment(Long articleId, String text) {
        this.articleId = articleId;
        this.text = text;
    }

    public Comment setId(Long id) {
        return create(id, articleId, text);
    }

    public Comment setArticle(Long article) {
        return create(id, article, text);
    }

    public Comment setText(String text) {
        return create(id, articleId, text);
    }

    private Comment create(Long id, Long article, String text) {
        return new Comment(id, article, text);
    }
}
