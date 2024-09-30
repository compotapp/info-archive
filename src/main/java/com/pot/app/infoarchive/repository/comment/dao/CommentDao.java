package com.pot.app.infoarchive.repository.comment.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDao {

    private Long id;
    private Long articleId;
    private String text;
}
