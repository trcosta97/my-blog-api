package com.myBlog.api.dto.account;

import com.myBlog.api.domain.account.Account;

public record ShowAccountDTO(Long id, String name, String lastName, String login, String password, String birthDate) {
    public ShowAccountDTO(Account account) {
        this(account.getId(), account.getName(), account.getLastName(), account.getLogin(), account.getPassword(), account.getBirthDate().toString());
    }
}
