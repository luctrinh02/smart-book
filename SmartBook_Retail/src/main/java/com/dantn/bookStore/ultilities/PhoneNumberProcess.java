package com.dantn.bookStore.ultilities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberProcess implements ConstraintValidator<PhoneNumerChecking, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value==null || "".equals(value)) {
			return false;
		}else {
			String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
			return value.matches(reg);
		}
	}
	
}
