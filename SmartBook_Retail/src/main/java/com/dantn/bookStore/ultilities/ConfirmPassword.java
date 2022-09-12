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
@Constraint(validatedBy = ConfirmPasswordProcess.class)
public @interface ConfirmPassword {
	String message() default "Xác nhận mật khẩu không trùng khớp";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
