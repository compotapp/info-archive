package com.pot.app.infoarchive.repository.article;

import com.pot.app.infoarchive.domain.article.Article;
import com.pot.app.infoarchive.repository.article.dao.ArticleDao;
import com.pot.app.infoarchive.repository.comment.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.pot.app.infoarchive.repository.article.mapping.ArticleDaoMapping.toDao;
import static com.pot.app.infoarchive.repository.article.mapping.ArticleDaoMapping.toEntity;

@Service
public class ArticleRepositoryImpl implements ArticleRepository {

    private final ConcurrentHashMap<Long, ArticleDao> repository = new ConcurrentHashMap<>();
    private final CommentRepository commentRepository;

    public ArticleRepositoryImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Article save(Article article) {
        var dao = toDao(article);
        repository.put(dao.getId(), dao);
        return toEntity(dao, commentRepository.getByArticleId(dao.getId()));
    }

    @Override
    public Article getById(Long id) {
        var dao = repository.get(id);
        return toEntity(dao, commentRepository.getByArticleId(dao.getId()));
    }

    @Override
    public List<Article> getAll() {
        return repository
                .values()
                .stream()
                .map(dao -> toEntity(dao, commentRepository.getByArticleId(dao.getId())))
                .sorted(Comparator.comparingLong(Article::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Article update(Article article) {
        var dao = toDao(article);
        repository.put(dao.getId(), dao);
        return toEntity(dao, commentRepository.getByArticleId(dao.getId()));
    }

    @Override
    public void deleteById(Long id) {
        repository.remove(id);
        commentRepository.deleteByArticleId(id);
    }
}
