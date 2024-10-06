package com.pot.app.infoarchive.repository.comment;

import com.pot.app.infoarchive.domain.comment.Comment;
import com.pot.app.infoarchive.domain.comment.dto.CommentResponse;
import com.pot.app.infoarchive.service.comment.mapping.CommentMapping;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.pot.app.infoarchive.service.comment.mapping.CommentMapping.toDao;
import static com.pot.app.infoarchive.service.comment.mapping.CommentMapping.toEntity;

@Service
public class CommentRepositoryImpl implements CommentRepository {

    private final ConcurrentHashMap<String, CommentResponse> repository = new ConcurrentHashMap<>();

    @Override
    public Comment save(Comment comment) {
        var dao = toDao(comment);
        repository.put(dao.getCommentId(), dao);
        return toEntity(dao);
    }

    @Override
    public Comment getById(String id) {
        return toEntity(repository.get(id));
    }

    @Override
    public List<Comment> getAll() {
        return toEntity(repository.values());

    }

    @Override
    public List<Comment> getByArticleId(String id) {
        return repository.values()
                .stream()
                .filter(commentDao -> Objects.equals(commentDao.getArticleId(), id))
                .map(CommentMapping::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Comment update(Comment comment) {
        var dao = repository.get(comment.getCommentId().toString());
        dao.setText(comment.getText());
        repository.put(dao.getCommentId(), dao);
        return toEntity(dao);
    }

    @Override
    public void deleteById(String id) {
        repository.remove(id);
    }

    @Override
    public void deleteByArticleId(String id) {
        repository.values()
                .stream()
                .filter(commentDao -> Objects.equals(commentDao.getArticleId(), id))
                .map(CommentResponse::getCommentId)
                .forEach(repository::remove);
    }

    @Override
    public void deleteAll() {
        repository.clear();
    }
}
