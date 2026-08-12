package com.example.demo.exception;

@SuppressWarnings(value = "serial")
public class OrderNotFoundException extends ResourceNotFoundException {
	public OrderNotFoundException(String message) {
		super(message);
	}

	public OrderNotFoundException(Long id) {
		super("Order id is not exists: " + id);
	}
}
