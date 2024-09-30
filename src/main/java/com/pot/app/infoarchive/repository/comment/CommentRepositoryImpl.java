package com.pot.app.infoarchive.repository.comment;

import com.pot.app.infoarchive.domain.comment.Comment;
import com.pot.app.infoarchive.repository.comment.dao.CommentDao;
import com.pot.app.infoarchive.repository.comment.mapping.CommentDaoMapping;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.pot.app.infoarchive.repository.comment.mapping.CommentDaoMapping.toDao;
import static com.pot.app.infoarchive.repository.comment.mapping.CommentDaoMapping.toEntity;

@Service
public class CommentRepositoryImpl implements CommentRepository {

    private final ConcurrentHashMap<Long, CommentDao> repository = new ConcurrentHashMap<>();

    @Override
    public Comment save(Comment comment) {
        var dao = toDao(comment);
        repository.put(dao.getId(), dao);
        return toEntity(dao);
    }

    @Override
    public Comment getById(Long id) {
        return toEntity(repository.get(id));
    }

    @Override
    public List<Comment> getAll() {
        return toEntity(repository.values());

    }

    @Override
    public List<Comment> getByArticleId(Long id) {
        return repository.values()
                .stream()
                .filter(commentDao -> Objects.equals(commentDao.getArticleId(), id))
                .map(CommentDaoMapping::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Comment update(Comment comment) {
        var dao = toDao(comment);
        repository.put(dao.getId(), dao);
        return toEntity(dao);
    }

    @Override
    public void deleteById(Long id) {
        repository.remove(id);
    }

    @Override
    public void deleteByArticleId(Long id) {
        repository.values()
                .stream()
                .filter(commentDao -> Objects.equals(commentDao.getArticleId(), id))
                .map(CommentDao::getId)
                .forEach(repository::remove);
    }
}
