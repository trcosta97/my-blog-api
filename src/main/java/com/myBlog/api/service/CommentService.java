package com.myBlog.api.service;

import com.myBlog.api.domain.Account;
import com.myBlog.api.domain.Comment;
import com.myBlog.api.domain.Post;
import com.myBlog.api.exception.EntityNotFoundException;
import com.myBlog.api.repository.CommentRepository;
import com.myBlog.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
}



