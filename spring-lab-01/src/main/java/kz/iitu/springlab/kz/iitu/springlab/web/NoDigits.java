package kz.iitu.springlab.kz.iitu.springlab.web;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = NoDigitsValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface NoDigits {

    String message() default "Digits are not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
