package com.pot.app.infoarchive.service.comment.mapping;

import com.pot.app.infoarchive.domain.article.Article;
import com.pot.app.infoarchive.domain.comment.Comment;
import com.pot.app.infoarchive.domain.comment.dto.CommentCreateRequest;
import com.pot.app.infoarchive.domain.comment.dto.CommentResponse;
import com.pot.app.infoarchive.domain.comment.dto.CommentUpdateRequest;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.pot.app.infoarchive.repository.extension.GenerateId.generateCommentId;

public class CommentMapping {

    public static Comment toEntity(CommentResponse dto) {
        return new Comment(
                new Comment.CommentId(UUID.fromString(dto.getCommentId())),
                new Article.ArticleId(UUID.fromString(dto.getArticleId())),
                dto.getText()
        );
    }

    public static Comment toEntity(CommentCreateRequest dto) {
        return new Comment(
                new Article.ArticleId(UUID.fromString(dto.getArticleId())),
                dto.getText()
        );
    }

    public static Comment toEntity(CommentUpdateRequest dto) {
       return new Comment(
                new Comment.CommentId(UUID.fromString(dto.getCommentId())),
                dto.getText()
        );
    }


    public static CommentResponse toDto(Comment comment) {
        return new CommentResponse(
                comment.getCommentId().toString(),
                comment.getArticleId().toString(),
                comment.getText()
        );
    }

    public static List<CommentResponse> toDto(List<Comment> comment) {
        return comment
                .stream()
                .map(CommentMapping::toDto)
                .collect(Collectors.toList());
    }

    public static CommentResponse toDao(Comment comment) {
        final Comment.CommentId id = (comment.getCommentId() == null) ? generateCommentId() : comment.getCommentId();
        return new CommentResponse(
                id.toString(),
                comment.getArticleId().toString(),
                comment.getText()
        );
    }

    public static List<Comment> toEntity(Collection<CommentResponse> dao) {
        return dao
                .stream()
                .map(CommentMapping::toEntity)
                .collect(Collectors.toList());
    }
}
