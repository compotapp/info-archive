package com.pot.app.infoarchive.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private String commentId;
    private String articleId;
    private String text;
}
