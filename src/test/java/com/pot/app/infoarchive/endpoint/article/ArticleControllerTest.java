package com.pot.app.infoarchive.endpoint.article;

import com.pot.app.infoarchive.InfoArchiveApplication;
import com.pot.app.infoarchive.domain.article.dto.ArticleCreateRequest;
import com.pot.app.infoarchive.domain.article.dto.ArticleResponse;
import com.pot.app.infoarchive.service.article.ArticleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private ArticleService articleService;

    @Test
    void shouldCreateArticleDto10(){
        final var setTags = Set.of("Hello", "World");
        final var one = "one";
        final ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest(one, setTags);

        ResponseEntity<ArticleResponse> response = restTemplate.postForEntity(
                "/article/create",
                articleCreateRequest,
                ArticleResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        final var result = response.getBody();
        assertEquals(one, result.getName());
        assertEquals(setTags, result.getTags());
        assertEquals(emptyList(), result.getComments());
        assertNotNull(result.getArticleId());
    }

    @Test
    @Disabled
    void shouldCreateArticleDto() {
        //подготовка
        ArticleService articleService = Mockito.mock(ArticleService.class);
        InfoArchiveApplication application = new InfoArchiveApplication();
        final var setTags = Set.of("Hello", "World");
        final var one = "one";
        final String id = UUID.randomUUID().toString();
        final ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest(one, setTags);
        final ArticleResponse articleDto = new ArticleResponse(id, one, setTags, emptyList());

        //действие
        final OngoingStubbing<ArticleResponse> articleDtoOngoingStubbing = Mockito.when(articleService.save(articleCreateRequest)).thenReturn(articleDto);

        //проверка
        Assertions.assertTrue(articleDtoOngoingStubbing.getMock());

    }
}
