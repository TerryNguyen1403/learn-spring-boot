package com.example.demo.dto;

import com.example.demo.validation.ConfirmPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@ConfirmPassword
public record RegistryRequestDTO(
		@NotBlank(message = "Name must not be blank") @Size(min = 2, message = "Username must be at least 2 characters") String name,
		@NotBlank(message = "Email must not be blank") @Email(message = "Invalid email format") String email,
		@Size(min = 8, message = "Password must be at least 8 characters") String password, String confirmPassword) {
}
