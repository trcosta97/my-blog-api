package com.myBlog.api.domain;

import com.myBlog.api.dto.comment.AddCommentDTO;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;


@Entity
@Table(name = "tb_comments")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title",nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @Column(name = "creationDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;
    @Column(name = "lastUpdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdate;
    @Column(name = "active",  columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;


    public Comment(AddCommentDTO data) {
        setContent(data.content());
        setTitle(data.title());
        setAccount(new Account(data.accountId()));

    }

    @PrePersist
    public void prePersist() {
        setCreationDate(LocalDateTime.now());
        setLastUpdate(LocalDateTime.now());
    }
}

