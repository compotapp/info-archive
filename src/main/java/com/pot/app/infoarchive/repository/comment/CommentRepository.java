package com.pot.app.infoarchive.repository.comment;

import com.pot.app.infoarchive.domain.comment.Comment;

import java.util.List;

public interface CommentRepository {

    Comment save(Comment comment);

    Comment getById(Long id);

    List<Comment> getAll();

    List<Comment> getByArticleId(Long id);

    Comment update(Comment comment);

    void deleteById(Long id);

    void deleteByArticleId(Long id);
}
