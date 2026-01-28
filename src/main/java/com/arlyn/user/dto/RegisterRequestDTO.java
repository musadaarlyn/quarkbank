package com.arlyn.user.dto;

import com.arlyn.common.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public class RegisterRequestDTO {

    @NotBlank(message = "Name is required.")
    @Size(min = 1, max = 100)
    public String fullName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    @Size(max = 100)
    public String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 255)
    public String password;

    @NotNull(message = "Account type is required.")
    public AccountType account_type;
}
