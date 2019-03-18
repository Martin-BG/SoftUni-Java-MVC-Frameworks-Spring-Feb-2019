package org.softuni.residentevil.annotations.composite.virus;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
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

    long MIN = 1L;
    long MAX = 12L;

    @OverridesAttribute(constraint = Min.class, name = "value") long min() default MIN;

    @OverridesAttribute(constraint = Max.class, name = "value") long max() default MAX;

    String message() default "{virus.hours-until-mutation.range}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
