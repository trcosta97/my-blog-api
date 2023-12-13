package com.myBlog.api.domain.account;


import com.myBlog.api.dto.account.CreateAccountDTO;
import com.myBlog.api.dto.account.UpdateAccountDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity(name = "account")
@Table(name = "tb_account")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Account implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name="birthDate")
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
    @Column(name = "active",  columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;

    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private Roles roles;

    public Account(CreateAccountDTO data) {
        this.name = data.name();
        this.lastName = data.lastName();
        this.login = data.login();
        this.password = data.password();
        this.birthDate = data.birthDate();
    }

    public Account(UpdateAccountDTO data) {
        this.name = data.name();
        this.lastName = data.lastName();
        this.login = data.login();
        this.password = data.password();
    }

    public Account(Long id){
        this.id = id;
    }

    @PrePersist
    private void prePersist() {
        this.creationDate = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
        this.active = true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.roles == Roles.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
