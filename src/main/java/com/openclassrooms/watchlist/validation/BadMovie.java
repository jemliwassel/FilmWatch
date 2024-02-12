package com.openclassrooms.watchlist.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BadMovieValidator.class)
public @interface BadMovie {
	String message() default "Enter at least 15 characters in the comment section. You need to have a good reason to watch a bad movie, don’t you?" ;
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
