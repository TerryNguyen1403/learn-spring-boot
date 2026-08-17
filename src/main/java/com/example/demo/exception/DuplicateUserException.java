package com.example.demo.exception;

@SuppressWarnings("serial")
public class DuplicateUserException extends RuntimeException {
	public DuplicateUserException(String message) {
		super(message);
	}
}
