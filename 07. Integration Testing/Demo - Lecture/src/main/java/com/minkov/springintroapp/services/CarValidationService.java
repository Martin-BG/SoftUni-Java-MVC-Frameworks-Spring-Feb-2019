package com.minkov.springintroapp.services;

import com.minkov.springintroapp.entities.Car;

public interface CarValidationService {
    boolean isCarValid(Car car);
}
