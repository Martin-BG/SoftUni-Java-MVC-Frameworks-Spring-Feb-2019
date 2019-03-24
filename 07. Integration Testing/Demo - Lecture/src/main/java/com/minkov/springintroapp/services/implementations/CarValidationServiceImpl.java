package com.minkov.springintroapp.services.implementations;

import com.minkov.springintroapp.entities.Car;
import com.minkov.springintroapp.services.CarValidationService;
import org.springframework.stereotype.Service;

@Service
public class CarValidationServiceImpl implements CarValidationService {
    @Override
    public boolean isCarValid(Car car) {
        return car != null &&
                car.getModel() != null;
    }
}
