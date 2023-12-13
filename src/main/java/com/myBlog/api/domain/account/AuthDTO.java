package com.myBlog.api.domain.account;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(
        @NotBlank(message = "Preencha o campo de email")
        String login,
        @NotBlank(message = "Preencha o campo de senha")
        String senha) {
}
