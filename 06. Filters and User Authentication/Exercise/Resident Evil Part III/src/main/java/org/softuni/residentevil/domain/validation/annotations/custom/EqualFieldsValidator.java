package org.softuni.residentevil.domain.validation.annotations.custom;

import lombok.extern.java.Log;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Validation logic for {@link EqualFields} class-type annotation.
 * <p>
 * Returns {@code true} if all specified fields have equal non-null values or are {@code null}.
 * <ul>Throws {@link IllegalArgumentException} in case of:
 * <li>Less than 2 field names given for validation</li>
 * <li>Invalid field name</li>
 * <li>Inaccessible filed</li>
 * </ul>
 * see {@link EqualFields}
 */

@Log
public class EqualFieldsValidator implements ConstraintValidator<EqualFields, Object> {

    private List<String> fieldsToValidate = List.of();

    private static Optional<Object> getFieldValue(Field field, Object obj) {
        try {
            field.setAccessible(true);
            return Optional.ofNullable(field.get(obj));
        } catch (IllegalAccessException e) {
            String message = MessageFormat
                    .format("[{0}] [@EqualFields validation] Failed to get value of: {1}",
                            obj.getClass().getName(),
                            field.getName());
            throw new IllegalArgumentException(message, e);
        }
    }

    @Override
    public void initialize(EqualFields constraintAnnotation) {
        fieldsToValidate = Arrays
                .stream(constraintAnnotation.value().length > 0 ?
                        constraintAnnotation.value() :
                        constraintAnnotation.fields())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (fieldsToValidate.size() <= 1) {
            String message = MessageFormat
                    .format("[{0}] [@EqualFields validation] At least two fields for validation must be specified: {1}",
                            obj.getClass().getName(),
                            String.join(", ", fieldsToValidate));
            throw new IllegalArgumentException(message);
        }

        List<String> fieldsNotFound = new ArrayList<>(fieldsToValidate);

        List<Object> fieldValues = Arrays
                .stream(obj.getClass().getDeclaredFields())
                .filter(field -> fieldsToValidate.contains(field.getName()))
                .peek(field -> fieldsNotFound.remove(field.getName()))
                .map(field -> getFieldValue(field, obj))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // Not all required for validation fields found in class
        if (!fieldsNotFound.isEmpty()) {
            String message = MessageFormat
                    .format("[{0}] [@EqualFields validation] Field(s) to validate not found: {1}",
                            obj.getClass().getName(),
                            String.join(", ", fieldsNotFound));
            throw new IllegalArgumentException(message);
        }

        // Mix of null (filtered-out) and non-null values for fields -> not equal by contract
        if (fieldValues.size() != fieldsToValidate.size()) {
            return false;
        }

        // All fields have null values -> equal by contract
        if (fieldValues.isEmpty()) {
            return true;
        }

        // Check equality of field values
        return fieldValues.stream()
                .distinct()
                .limit(2L)
                .count() <= 1L;
    }
}
