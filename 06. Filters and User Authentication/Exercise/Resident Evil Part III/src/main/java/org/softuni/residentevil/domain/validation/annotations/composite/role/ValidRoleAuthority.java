package org.softuni.residentevil.domain.validation.annotations.composite.role;

import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Should start with ROLE_,
 * contain only A-Z and _ (cannot be last character),
 * length {@value MIN_LENGTH}-{@value MAX_LENGTH}
 */

@NotBlank
@Length(min = ValidRoleAuthority.MIN_LENGTH, max = ValidRoleAuthority.MAX_LENGTH)
@Pattern(regexp = "^ROLE_[A-Z_]{1,27}(?<=[^_])$")
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidRoleAuthority {

    int MIN_LENGTH = 1;
    int MAX_LENGTH = 32;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
