package com.pot.app.infoarchive.endpoint.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.comment.dto.CommentCreateRequest;
import com.pot.app.infoarchive.domain.comment.dto.CommentResponse;
import com.pot.app.infoarchive.domain.comment.dto.CommentUpdateRequest;
import com.pot.app.infoarchive.service.comment.CommentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CommentControllerHttpTest {

    private static HttpClient client;
    private static ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Autowired
    private CommentService commentService;

    @BeforeAll
    public static void init() {
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void clearRepository() {
        commentService.deleteAll();
    }

    @Test
    public void shouldCommentCreate() throws IOException, InterruptedException {
        final var articleId = UUID.randomUUID().toString();
        final var text = "Hello, Amigo";
        final var request = new CommentCreateRequest(articleId, text);
        final var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/comment/create", port)))
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(request)))
                .build();

        final var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        final var comment = objectMapper.readValue(response.body(), CommentResponse.class);
        assertNotNull(comment.getCommentId());
        assertEquals(articleId, comment.getArticleId());
        assertEquals(text, comment.getText());
    }

    @Test
    public void shouldCommentUpdate() throws IOException, InterruptedException {
        final var comment = createComment();
        final var updateText = "Hello, Friend";
        final var commentUpdateRequest = new CommentUpdateRequest(comment.getCommentId(), updateText);
        final var updateRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/comment/update", port)))
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(commentUpdateRequest)))
                .build();

        final var updateResponse = client.send(updateRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), updateResponse.statusCode());
        final var updateComment = objectMapper.readValue(updateResponse.body(), CommentResponse.class);
        assertEquals(comment.getCommentId(), updateComment.getCommentId());
        assertEquals(comment.getArticleId(), updateComment.getArticleId());
        assertEquals(updateText, updateComment.getText());
    }

    @Test
    public void shouldGetByCommentId() throws IOException, InterruptedException {
        final var comment = createComment();
        final var idDto = new IdDto(comment.getCommentId());
        final var commentIdRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/comment/get", port)))
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(idDto)))
                .build();

        final var response = client.send(commentIdRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        final var commentResponse = objectMapper.readValue(response.body(), CommentResponse.class);
        assertEquals(comment.getCommentId(), commentResponse.getCommentId());
        assertEquals(comment.getArticleId(), commentResponse.getArticleId());
        assertEquals(comment.getText(), commentResponse.getText());
    }

    @Test
    public void shouldGetAll() throws IOException, InterruptedException {
        createComment();
        createComment();
        final var articleGetAllRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/comment/get/all", port)))
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .GET()
                .build();

        final var response = client.send(articleGetAllRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        final var comments = objectMapper.readValue(response.body(), List.class);
        assertEquals(comments.size(), 2);
    }

    @Test
    public void shouldArticleDelete() throws IOException, InterruptedException {
        final var comment = createComment();
        final var idDto = new IdDto(comment.getCommentId());
        final var commentIdRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/comment/delete", port)))
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(idDto)))
                .build();
        final var deleteResponse = client.send(commentIdRequest, HttpResponse.BodyHandlers.ofString());
        final var commentGetAllRequest = HttpRequest.newBuilder()
                .uri(URI.create(format("http://localhost:%s/comment/get/all", port)))
                .headers("Content-Type", APPLICATION_JSON_VALUE)
                .GET()
                .build();

        final var response = client.send(commentGetAllRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), deleteResponse.statusCode());
        assertEquals(HttpStatus.OK.value(), response.statusCode());
        final var comments = objectMapper.readValue(response.body(), List.class);
        assertEquals(comments.size(), 0);
    }

    private CommentResponse createComment() {
        final var articleId = UUID.randomUUID().toString();
        final var text = "Hello, Amigo";
        final var commentCreateRequest = new CommentCreateRequest(articleId, text);

        return commentService.save(commentCreateRequest);
    }
}