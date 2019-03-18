package org.softuni.residentevil.annotations.composite.virus;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Hours Until Turn (to a mutation) – Number, between 1 and 12.
 */

@NotNull
@Min(ValidVirusHoursUntilMutation.MIN)
@Max(ValidVirusHoursUntilMutation.MAX)
@ReportAsSingleViolation
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidVirusHoursUntilMutation {

    int MIN = 1;
    int MAX = 12;

    String message() default "Hours until mutation should be between 1 and 12";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
