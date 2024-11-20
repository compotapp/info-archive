package com.pot.app.infoarchive.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {

    @NotNull
    @NotBlank
    private String articleId;
    @NotNull
    @NotBlank
    private String text;
}