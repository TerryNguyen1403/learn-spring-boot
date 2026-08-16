package com.example.demo.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidation implements ConstraintValidator<StrongPassword, String> {
	private int minLength;
	private boolean requireSpecial;

	// Regex pattern
	private static final Pattern HAS_LOWER = Pattern.compile("[a-z]");
	private static final Pattern HAS_UPPER = Pattern.compile("[A-Z]");
	private static final Pattern HAS_DIGIT = Pattern.compile("\\d");
	private static final Pattern HAS_SPECIAL = Pattern.compile("[@#$%^&+=!]");

	@Override
	public void initialize(StrongPassword annotation) {
		this.minLength = annotation.minLength();
		this.requireSpecial = annotation.requireSpecial();
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if (password == null) {
			return true;
		}

		StringBuilder errors = new StringBuilder();
		if (password.length() < minLength)
			errors.append(minLength + " characters; ");
		if (!HAS_LOWER.matcher(password).find())
			errors.append("1 lower case; ");
		if (!HAS_UPPER.matcher(password).find())
			errors.append("1 upper case; ");
		if (!HAS_DIGIT.matcher(password).find())
			errors.append("1 digit; ");
		if (!HAS_SPECIAL.matcher(password).find() && requireSpecial == true)
			errors.append("1 special character");

		if (errors.length() > 0) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Password must have at least " + errors)
					.addConstraintViolation();

			return false;
		}

		return true;
	}

}
