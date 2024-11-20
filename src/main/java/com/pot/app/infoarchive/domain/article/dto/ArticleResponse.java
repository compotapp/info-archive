package com.pot.app.infoarchive.domain.article.dto;

import com.pot.app.infoarchive.domain.comment.dto.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {

    private String articleId;
    private String name;
    private Set<String> tags;
    private List<CommentResponse> comments;
}
