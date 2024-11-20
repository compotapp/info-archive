package com.pot.app.infoarchive.repository.article;

import com.pot.app.infoarchive.domain.article.Article;
import com.pot.app.infoarchive.domain.article.dto.ArticleUpdateRequest;
import com.pot.app.infoarchive.repository.comment.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.pot.app.infoarchive.service.article.mapping.ArticleMapping.toDao;
import static com.pot.app.infoarchive.service.article.mapping.ArticleMapping.toEntity;

@Service
public class ArticleRepositoryImpl implements ArticleRepository {

    private final ConcurrentHashMap<String, ArticleUpdateRequest> repository = new ConcurrentHashMap<>();
    private final CommentRepository commentRepository;

    public ArticleRepositoryImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Article save(Article article) {
        var dao = toDao(article);
        repository.put(dao.getArticleId(), dao);
        return toEntity(dao, commentRepository.getByArticleId(dao.getArticleId()));
    }

    @Override
    public Article getById(String id) {
        var dao = repository.get(id);
        return toEntity(dao, commentRepository.getByArticleId(dao.getArticleId()));
    }

    @Override
    public List<Article> getAll() {
        return repository
                .values()
                .stream()
                .map(dao -> toEntity(dao, commentRepository.getByArticleId(dao.getArticleId())))
                .collect(Collectors.toList());
    }

    @Override
    public Article update(Article article) {
        var dao = toDao(article);
        repository.put(dao.getArticleId(), dao);
        return toEntity(dao, commentRepository.getByArticleId(dao.getArticleId()));
    }

    @Override
    public void deleteById(String id) {
        repository.remove(id);
        commentRepository.deleteByArticleId(id);
    }

    @Override
    public void deleteAll() {
        repository.clear();
        commentRepository.deleteAll();
    }
}
