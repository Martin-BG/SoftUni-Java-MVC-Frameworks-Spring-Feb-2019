package org.softuni.exodia.annotations.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Values of all listed fields in the annotated class should be equal.
 * <p>
 * Fields could be of any type that implements equals().
 * {@code null} values are also included in validation.
 */
@Repeatable(EqualFields.List.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EqualFieldsValidator.class)
@Documented
public @interface EqualFields {
    String message() default "Field values do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] value() default {};

    /**
     * Defines several {@link EqualFields} annotations on the same element.
     *
     * @see EqualFields
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        EqualFields[] value();
    }
}
