package org.softuni.residentevil.domain.validation.annotations.composite.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Should be properly formatted email, length {@value MIN_LENGTH}-{@value MAX_LENGTH}
 */

@Email(message = "{user.email.invalid}")
@Size(message = "{user.email.length}", min = ValidUserEmail.MIN_LENGTH, max = ValidUserEmail.MAX_LENGTH)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidUserEmail {

    int MIN_LENGTH = 1;
    int MAX_LENGTH = 64;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
