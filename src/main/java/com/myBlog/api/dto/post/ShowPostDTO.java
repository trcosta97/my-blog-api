package com.myBlog.api.dto.post;

import com.myBlog.api.domain.Post;
import com.myBlog.api.dto.account.ShowAccountDTO;

public record ShowPostDTO(Long id, String title, String content, ShowAccountDTO account) {
    public ShowPostDTO(Post post) {
        this(post.getId(), post.getTitle(), post.getContent(), new ShowAccountDTO(post.getAuthor()));
    }
}
