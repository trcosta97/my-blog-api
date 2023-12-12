package com.myBlog.api.repository;

import com.myBlog.api.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.author.id = :accountId")
    Page<Post> findByAccountId(Long accountId, PageRequest pagination);
}
