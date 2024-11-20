package com.pot.app.infoarchive.endpoint.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.article.dto.ArticleCreateRequest;
import com.pot.app.infoarchive.domain.article.dto.ArticleResponse;
import com.pot.app.infoarchive.domain.article.dto.ArticleUpdateRequest;
import com.pot.app.infoarchive.service.article.ArticleService;
import com.pot.app.infoarchive.testUtil.TestBDFacade;
import com.pot.app.infoarchive.testUtil.annotation.ClickHouse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ClickHouse
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ArticleControllerHttpTest {

    private static HttpClient client;
    private static ObjectMapper objectMapper;

    @Autowired
    private TestBDFacade bd;

    @LocalServerPort
    private int port;

    @Autowired
    private ArticleService articleService;

    @BeforeAll
    public static void init() {
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    public void setUpDb() {
        bd.cleanDatabase();
        bd.createMyTable();
    }

    @AfterEach
    public void clearRepository() {
        articleService.deleteAll();
    }

    @Test
    @SneakyThrows
    public void shouldArticleCreate() {
        final var articleName = "Amigo";
        final var setTags = Set.of("Hello", "Amigo");
        final var request = new ArticleCreateRequest(articleName, setTags);
        final var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/article/create", port)))//вынести в константу
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(request)))
                .build();//вынести в отднльный метод

        final var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        final var article = objectMapper.readValue(response.body(), ArticleResponse.class);
        assertNotNull(article.getArticleId());
        assertEquals(articleName, article.getName());
        assertEquals(setTags, article.getTags());
        assertEquals(emptyList(), article.getComments());
    }

    @Test
    @SneakyThrows
    public void shouldArticleUpdate() {
        final var article = createArticle();
        final var updateArticleName = "Friend";
        final var updateSetTags = Set.of("Hello", "Friend");
        final var articleUpdateRequest = new ArticleUpdateRequest(
                article.getArticleId(),
                updateArticleName,
                updateSetTags
        );
        final var updateRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/article/update", port)))
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(articleUpdateRequest)))
                .build();

        final var updateResponse = client.send(updateRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), updateResponse.statusCode());
        final var updateArticle = objectMapper.readValue(updateResponse.body(), ArticleResponse.class);
        assertEquals(article.getArticleId(), updateArticle.getArticleId());
        assertEquals(updateArticleName, updateArticle.getName());
        assertEquals(updateSetTags, updateArticle.getTags());
        assertEquals(emptyList(), updateArticle.getComments());
    }

    @Test
    @SneakyThrows
    public void shouldGetByArticleId() {
        final var article = createArticle();
        final var idDto = new IdDto(article.getArticleId());
        final var articleIdRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/article/get", port)))
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(idDto)))
                .build();

        final var response = client.send(articleIdRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        final var articleResponse = objectMapper.readValue(response.body(), ArticleResponse.class);
        assertEquals(article.getArticleId(), articleResponse.getArticleId());
        assertEquals(article.getName(), articleResponse.getName());
        assertEquals(article.getTags(), articleResponse.getTags());
        assertEquals(emptyList(), articleResponse.getComments());
    }

    @Test
    @SneakyThrows
    public void shouldGetAll() {
        createArticle();
        createArticle();
        final var articleGetAllRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/article/get/all", port)))
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .GET()
                .build();

        final var response = client.send(articleGetAllRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        final var articles = objectMapper.readValue(response.body(), List.class);
        assertEquals(articles.size(), 2);
    }

    @Test
    @SneakyThrows
    public void shouldArticleDelete() {
        final var article = createArticle();
        final var idDto = new IdDto(article.getArticleId());
        final var articleIdRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/article/delete", port)))
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(idDto)))
                .build();
        final var deleteResponse = client.send(articleIdRequest, HttpResponse.BodyHandlers.ofString());
        final var articleGetAllRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/article/get/all", port)))
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .GET()
                .build();

        final var response = client.send(articleGetAllRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), deleteResponse.statusCode());
        assertEquals(HttpStatus.OK.value(), response.statusCode());
        final var articles = objectMapper.readValue(response.body(), List.class);
        assertEquals(articles.size(), 0);
    }

    private ArticleResponse createArticle() {
        final var articleName = "Amigo";
        final var setTags = Set.of("Hello", "Amigo");
        final var articleCreateRequest = new ArticleCreateRequest(articleName, setTags);

        return articleService.save(articleCreateRequest);
    }
}

