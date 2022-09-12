package com.dantn.bookStore.ultilities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dantn.bookStore.dto.request.UserRequest;


public class ConfirmPasswordProcess implements ConstraintValidator<ConfirmPassword, UserRequest>{

	@Override
	public boolean isValid(UserRequest value, ConstraintValidatorContext context) {
		return value.getConfirm().equals(value.getPassword());
	}

}