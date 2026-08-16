package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserPatchDTO(@Size(min = 2, message = "Name must be at least 2 characters") String name,
		@Email(message = "Invalid email format") String email,
		@Size(min = 2, max = 20, message = "Password must be between 6 and 20 characters") String password) {
}
