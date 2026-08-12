package com.example.demo.exception;

import java.time.LocalDate;
import java.util.Map;

public class ValidationErrorResponse {
	private int statusCode;
	private String message;
	private Map<String, String> errors;
	private LocalDate timestamp;

	public ValidationErrorResponse(int statusCode, String message, Map<String, String> errors) {
		this.statusCode = statusCode;
		this.message = message;
		this.errors = errors;
		this.timestamp = LocalDate.now();
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}
}
