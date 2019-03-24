package com.minkov.springintroapp.validators;

import com.minkov.springintroapp.services.CarsService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CapitalCaseValidator implements ConstraintValidator<CapitalCase, String> {
    private final CarsService carsService;

    public CapitalCaseValidator(CarsService carsService) {
        this.carsService = carsService;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.chars()
                .allMatch(Character::isUpperCase);
    }
}
