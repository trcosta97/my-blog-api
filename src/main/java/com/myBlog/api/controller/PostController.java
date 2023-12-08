package com.myBlog.api.controller;

import com.myBlog.api.domain.Comment;
import com.myBlog.api.domain.Post;
import com.myBlog.api.dto.post.CreatePostDTO;
import com.myBlog.api.dto.comment.AddCommentDTO;
import com.myBlog.api.service.AccountService;
import com.myBlog.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/post")
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity create(@RequestBody CreatePostDTO data, UriComponentsBuilder uri){
        var post = new Post(data);
        var uriPost = uri.path("/post/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uriPost).body(postService.create(post));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(postService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        return ResponseEntity.ok(postService.getAll(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Post data){
        return ResponseEntity.ok(postService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        postService.logicalDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/comment")
    public ResponseEntity addComment(@PathVariable Long id, @RequestBody AddCommentDTO data){
        var comment = new Comment(data);
        comment.setAccount(accountService.getById(data.accountId()));
        comment.setPost(postService.getById(id));
        return ResponseEntity.ok(postService.addComment(id, comment));
    }
}
