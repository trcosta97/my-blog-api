package com.myBlog.api.service;

import com.myBlog.api.domain.account.Account;
import com.myBlog.api.domain.Comment;
import com.myBlog.api.repository.CommentRepository;
import com.myBlog.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public Comment create(Comment comment, Long postId) {
        postRepository.findById(postId).ifPresent(post -> {
            comment.setPost(post);
            comment.setCreationDate(LocalDateTime.now());
            comment.setLastUpdate(LocalDateTime.now());
            post.addComment(comment);
            postRepository.save(post);
        });
        comment.setAccount(new Account(comment.getAccount().getId()));
        return commentRepository.save(comment);
    }

    public Page<Comment> getAll(Long postId, Integer page, Integer size) {
        var pagination = PageRequest.of(page, size);
        return commentRepository.findByPostId(postId, pagination);
    }
}



