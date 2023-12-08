package com.myBlog.api.service;

import com.myBlog.api.domain.Comment;
import com.myBlog.api.domain.Post;
import com.myBlog.api.exception.EntityNotFoundException;
import com.myBlog.api.repository.CommentRepository;
import com.myBlog.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post create(Post post) {
        return postRepository.save(post);
    }

    public Post getById(Long id) {
        return postRepository.findById(id).get();
    }

    public Page<Post> getAll(Integer page, Integer size) {
        return postRepository.findAll(PageRequest.of(page, size));
    }

    public Post update(Long id, Post data) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post updatedPost = optionalPost.get();
            if(data.getTitle() != null)
                updatedPost.setTitle(data.getTitle());
            if(data.getContent() != null)
                updatedPost.setContent(data.getContent());
            updatedPost.setLastUpdate(LocalDateTime.now());
            return postRepository.save(updatedPost);
        }
        throw new EntityNotFoundException("Post not found");
    }

    public void logicalDelete(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post deletedPost = optionalPost.get();
            deletedPost.setActive(false);
            postRepository.save(deletedPost);
        }
    }

    public void delete(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post deletedPost = optionalPost.get();
            postRepository.delete(deletedPost);
        }
    }

    public Comment addComment(Long id, Comment comment) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.addComment(comment);
            return postRepository.save(post).getComments().get(post.getComments().size() - 1);
        }
        throw new EntityNotFoundException("Post not found");
    }

}
