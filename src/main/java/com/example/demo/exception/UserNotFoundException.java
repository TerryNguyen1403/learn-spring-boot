package com.example.demo.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends ResourceNotFoundException {
	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Long id) {
		super("User ID not found: " + id);
	}
}
