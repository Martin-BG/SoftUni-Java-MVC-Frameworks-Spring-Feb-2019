package org.softuni.residentevil.annotations.composite.virus;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Name – Cannot be empty, should be between 3 and 10 symbols.
 */

@NotBlank(message = "{virus.name.blank}")
@Size(message = "{virus.name.length}", min = ValidVirusName.MIN_LENGTH, max = ValidVirusName.MAX_LENGTH)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidVirusName {

    int MIN_LENGTH = 3;
    int MAX_LENGTH = 10;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
