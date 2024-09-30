package com.pot.app.infoarchive.repository.comment.mapping;

import com.pot.app.infoarchive.domain.comment.Comment;
import com.pot.app.infoarchive.repository.comment.dao.CommentDao;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.pot.app.infoarchive.repository.extension.GenerateId.generateCommentId;

public class CommentDaoMapping {

    public static CommentDao toDao(Comment comment) {
        var id = (comment.getId() == null) ? generateCommentId() : comment.getId();
        return new CommentDao(
                id,
                comment.getArticleId(),
                comment.getText()
        );
    }

    public static Comment toEntity(CommentDao dao) {
        return new Comment(
                dao.getId(),
                dao.getArticleId(),
                dao.getText()
        );
    }

    public static List<Comment> toEntity(Collection<CommentDao> dao) {
        return dao
                .stream()
                .map(CommentDaoMapping::toEntity)
                .sorted(Comparator.comparingLong(Comment::getId))
                .collect(Collectors.toList());
    }
}
