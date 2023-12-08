package com.myBlog.api.dto.post;

import com.myBlog.api.domain.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostDTO(
        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Content is required")
        @Size(min = 100, max = 10000, message = "Content must be at least 10 characters and maximum 10000 characters")
        String content,
        Account account){
}
