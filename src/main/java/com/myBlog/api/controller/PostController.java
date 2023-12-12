package com.myBlog.api.controller;

import com.myBlog.api.domain.Comment;
import com.myBlog.api.domain.Post;
import com.myBlog.api.dto.account.ShowAccountDTO;
import com.myBlog.api.dto.post.CreatePostDTO;
import com.myBlog.api.dto.comment.AddCommentDTO;
import com.myBlog.api.dto.post.ShowPostDTO;
import com.myBlog.api.dto.post.UpdatePostDTO;
import com.myBlog.api.repository.CommentRepository;
import com.myBlog.api.service.AccountService;
import com.myBlog.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/post")
@RestController
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody CreatePostDTO data, UriComponentsBuilder uri){
        var post = new Post(data);
        post.setAuthor(accountService.getById(data.author().id()));
        var uriPost = uri.path("/post/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uriPost).body(new ShowPostDTO(postService.create(post)));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(new ShowPostDTO(postService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<ShowPostDTO>> getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        var paginacao = postService.getAll(page, size).map(ShowPostDTO::new);
        return ResponseEntity.ok(paginacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UpdatePostDTO data){
        var updatedPost = new ShowPostDTO(postService.update(id, new Post(data)));
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        postService.logicalDelete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<ShowPostDTO>> getAllByAuthor(@PathVariable Long authorId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        var paginacao = postService.getAllByAccountId(authorId, page, size).map(ShowPostDTO::new);
        return ResponseEntity.ok(paginacao);
    }


}
