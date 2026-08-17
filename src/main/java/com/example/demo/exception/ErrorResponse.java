package com.example.demo.exception;

import java.time.LocalDate;

public record ErrorResponse(int status, String message, String path, LocalDate timestamp) {
	public ErrorResponse(int status, String message, String path) {
		this(status, message, path, LocalDate.now());
	}
}
