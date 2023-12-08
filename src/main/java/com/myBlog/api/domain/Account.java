package com.myBlog.api.domain;


import com.myBlog.api.dto.account.CreateAccountDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity(name = "account")
@Table(name = "tb_account")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name="birthDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "creationDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;
    @Column(name = "lastUpdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdate;
    @Column(name = "active", nullable = false,columnDefinition = "BIT(1) DEFAULT 1")

    private Boolean active;

    public Account(CreateAccountDTO data) {
        this.name = data.name();
        this.lastName = data.lastName();
        this.login = data.login();
        this.password = data.password();
    }

    @PrePersist
    private void prePersist() {
        this.creationDate = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
        this.active = true;
    }
}
