package com.pot.app.infoarchive.repository.comment;

import com.pot.app.infoarchive.domain.comment.Comment;

import java.util.List;

public interface CommentRepository {

    Comment save(Comment comment);

    Comment getById(String id);

    List<Comment> getAll();

    List<Comment> getByArticleId(String id);

    Comment update(Comment comment);

    void deleteById(String id);

    void deleteByArticleId(String id);

    void deleteAll();
}
