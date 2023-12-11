package com.myBlog.api.dto.account;

public record UpdateAccountDTO(
        String name,
        String lastName,
        String login,
        String password
) {
}
