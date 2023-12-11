package com.myBlog.api.dto.comment;

import com.myBlog.api.domain.Comment;

public record ShowCommentDTO(Long id, String title, String content, Long accountId, Long postId) {
    public ShowCommentDTO(Comment comment) {
        this(comment.getId(), comment.getTitle(), comment.getContent(), comment.getAccount().getId(), comment.getPost().getId());
    }
}
