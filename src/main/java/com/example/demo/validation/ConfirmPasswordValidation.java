package com.example.demo.validation;

import com.example.demo.dto.RegistryRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidation implements ConstraintValidator<ConfirmPassword, RegistryRequestDTO> {

	@Override
	public boolean isValid(RegistryRequestDTO request, ConstraintValidatorContext context) {
		if (request.password() == null || request.confirmPassword() == null) {
			return true;
		}

		if (!request.password().equals(request.confirmPassword())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Password do not match").addConstraintViolation();
			return false;
		}

		return true;
	}

}
