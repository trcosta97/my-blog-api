package com.myBlog.api.controller;

import com.myBlog.api.domain.Comment;
import com.myBlog.api.dto.comment.AddCommentDTO;
import com.myBlog.api.dto.comment.ShowCommentDTO;
import com.myBlog.api.service.AccountService;
import com.myBlog.api.service.CommentService;
import com.myBlog.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @PostMapping("/{postId}")
    public ResponseEntity<ShowCommentDTO> addComment(@PathVariable Long postId, @RequestBody AddCommentDTO data) {
        try {
            postService.verifyPostExists(postId);
            Comment comment = commentService.create(new Comment(data), postId);
            var showComment = new ShowCommentDTO(comment);
            return ResponseEntity.ok(showComment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
