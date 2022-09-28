package com.dantn.bookStore.ultilities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dantn.bookStore.dto.request.UserRequest;


public class ConfirmPasswordProcess implements ConstraintValidator<ConfirmPassword, UserRequest>{

	@Override
	public boolean isValid(UserRequest value, ConstraintValidatorContext context) {
		if(value.getPassword()==null || "".equals(value.getPassword())) {
			return true;
		}else {
			return value.getPassword().equals(value.getConfirm());
		}
	}

}