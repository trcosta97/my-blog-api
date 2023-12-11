package com.myBlog.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myBlog.api.dto.post.CreatePostDTO;
import com.myBlog.api.dto.post.UpdatePostDTO;
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
    @OneToMany( mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<Comment>();
    @Column(name = "creationDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;
    @Column(name = "lastUpdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdate;
    @Column(name = "active",  columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;

    public Post(CreatePostDTO data) {
        setTitle(data.title());
        setContent(data.content());
        setAuthor(new Account(data.author().id()));
    }

    public Post(UpdatePostDTO data) {
        setTitle(data.title());
        setContent(data.content());
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
