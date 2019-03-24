package com.minkov.springintroapp.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CapitalCaseValidator.class)
public @interface CapitalCase {
    String message() default "Some characters are lower case";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
