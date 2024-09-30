package com.pot.app.infoarchive.service.comment.mapping;

import com.pot.app.infoarchive.domain.comment.Comment;
import com.pot.app.infoarchive.domain.comment.dto.CommentCreate;
import com.pot.app.infoarchive.domain.comment.dto.CommentDto;
import com.pot.app.infoarchive.domain.comment.dto.CommentUpdate;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapping {

    public static Comment toEntity(CommentDto dto) {
        return new Comment(
                dto.getId(),
                dto.getArticleId(),
                dto.getText()
        );
    }

    public static Comment toEntity(CommentCreate dto) {
        return new Comment(
                dto.getArticleId(),
                dto.getText()
        );
    }

    public static Comment toEntity(CommentUpdate dto) {
        return new Comment(
                dto.getId(),
                dto.getText()
        );
    }


    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getArticleId(),
                comment.getText()
        );
    }

    public static List<CommentDto> toDto(List<Comment> comment) {
        return comment
                .stream()
                .map(CommentMapping::toDto)
                .collect(Collectors.toList());
    }
}
