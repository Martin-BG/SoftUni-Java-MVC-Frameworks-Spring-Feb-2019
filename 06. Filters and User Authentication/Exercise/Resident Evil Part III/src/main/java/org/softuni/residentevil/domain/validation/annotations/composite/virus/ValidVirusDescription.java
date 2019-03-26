package org.softuni.residentevil.domain.validation.annotations.composite.virus;

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
 * Description – Cannot be empty, should be between 5 and 100 symbols.<br>
 * Represented as Text in the database
 */

@NotBlank(message = "{virus.description.blank}")
@Size(message = "{virus.description.length}",
        min = ValidVirusDescription.MIN_LENGTH, max = ValidVirusDescription.MAX_LENGTH)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidVirusDescription {

    int MIN_LENGTH = 5;
    int MAX_LENGTH = 100;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
