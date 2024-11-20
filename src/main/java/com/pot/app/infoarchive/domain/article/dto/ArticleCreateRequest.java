package com.pot.app.infoarchive.domain.article.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateRequest {

    @NotNull
    @NotBlank
    private String name;
    @Nullable
    private Set<String> tags;
}
