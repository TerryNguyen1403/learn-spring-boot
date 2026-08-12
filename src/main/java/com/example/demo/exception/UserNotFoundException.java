package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ResourceNotFoundException {
	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Long id) {
		super("User ID not found: " + id);
	}
}
