package com.minkov.springintroapp.services;

import com.minkov.springintroapp.dtos.CarDto;
import com.minkov.springintroapp.entities.Car;

import java.util.List;

public interface CarsService {
    String[] getCarModels();

    Car[] getCarsByModel(String model);

    List<CarDto> getAllCars();

    void createCar(String model, String user);
}
