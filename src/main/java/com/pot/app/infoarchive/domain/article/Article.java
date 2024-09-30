package com.pot.app.infoarchive.domain.article;

import com.pot.app.infoarchive.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class Article{

    private Long id;
    private String name;
    private Set<String> tags;
    private List<Comment> comments;

    public Article(String name, Set<String> tags) {
        this.name = name;
        this.tags = tags;
    }

    public Article(Long id, String name, Set<String> tags) {
        this.id = id;
        this.name = name;
        this.tags = tags;
    }

    public Article setId(Long id) {
        return create(id, name, tags, comments);
    }

    public Article setName(String name) {
        return create(id, name, tags, comments);
    }

    public Article addTags(Set<String> tags) {
        this.tags.addAll(tags);
        return create(id, name, this.tags, comments);
    }

    public Article setTags(Set<String> tags) {
        return create(id, name, tags, comments);
    }

    public Article addComments(List<Comment> comments) {
        this.comments.addAll(comments);
        return create(id, name, tags, this.comments);
    }

    public Article setComments(List<Comment> comments) {
        return create(id, name, tags, comments);
    }

    private Article create(Long id, String name, Set<String> tags, List<Comment> comments) {
        return new Article(id, name, tags, comments);
    }
}
