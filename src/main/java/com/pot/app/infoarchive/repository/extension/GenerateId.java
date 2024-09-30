package com.pot.app.infoarchive.repository.extension;

public class GenerateId {

    private static Long articleId = 1L;
    private static Long commentId = 1L;

    synchronized public static Long generateArticleId() {
        return articleId++;
    }

    synchronized public static Long generateCommentId() {
        return commentId++;
    }
}
