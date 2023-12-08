package com.myBlog.api.dto.comment;

public record AddCommentDTO(String title, String content, Long accountId) {
}
