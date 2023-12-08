package com.myBlog.api.domain;

import com.myBlog.api.dto.post.CreatePostDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 10000)
    private String content;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account author;
    @OneToMany
    @JoinColumn(name = "comment_id", nullable = false)
    private List<Comment> comments;
    @Column(name = "creationDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;
    @Column(name = "lastUpdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdate;
    @Column(name = "active", nullable = false,columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean active;

    public Post(CreatePostDTO data) {
        setTitle(data.title());
        setContent(data.content());
        setAuthor(data.account());
    }

    @PrePersist
    public void prePersist() {
        setCreationDate(LocalDateTime.now());
        setLastUpdate(LocalDateTime.now());
        setComments(new ArrayList<>());
        setActive(true);
    }

    public void addComment(Comment comment){
        getComments().add(comment);
    }




}
