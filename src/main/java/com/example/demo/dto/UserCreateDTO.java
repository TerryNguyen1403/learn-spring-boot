package com.example.demo.dto;

import com.example.demo.validation.StrongPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(Long id,
		@NotBlank(message = "Name cannot be blank") @Size(min = 2, message = "Name must be at least 2 characters") String name,
		@NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String email,
		@NotBlank(message = "Password must not empty") @StrongPassword(requireSpecial = false) String password,
		@NotBlank(message = "Role cannot be blank") String role,
		@NotNull(message = "isActive cannot be null") boolean isActive) {
}
