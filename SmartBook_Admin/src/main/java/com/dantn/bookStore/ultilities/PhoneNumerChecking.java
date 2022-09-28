package com.dantn.bookStore.ultilities;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target({ FIELD, TYPE})
@Constraint(validatedBy = PhoneNumberProcess.class)
public @interface PhoneNumerChecking {
	String message() default "Số điện thoại không đúng định dạng";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
