package org.softuni.residentevil.annotations.composite.virus;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Creator
 * <ul>Should be one of these:
 * <li>Corp</li>
 * <li>corp</li>
 * </ul>
 */

@NotNull
@ReportAsSingleViolation
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidVirusCreator {

    int MAX_LENGTH = 4;

    String message() default "Creator should be either \"Corp\" or \"corp\"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
