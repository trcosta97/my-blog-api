package com.myBlog.api.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateAccountDTO(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Last name is required")
        String lastName,
        @Email(message = "Invalid email")
        String login,
//        @NotBlank(message = "Birth date is required")
        LocalDate birthDate,
        @NotBlank(message = "Password is required")
        String password
) {
}
