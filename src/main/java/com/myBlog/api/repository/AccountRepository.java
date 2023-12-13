package com.myBlog.api.repository;

import com.myBlog.api.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AccountRepository extends JpaRepository<Account, Long> {
    UserDetails findByLogin(String login);
}
