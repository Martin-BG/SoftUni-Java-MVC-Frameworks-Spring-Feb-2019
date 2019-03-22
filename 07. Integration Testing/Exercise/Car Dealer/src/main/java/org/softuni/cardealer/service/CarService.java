package org.softuni.cardealer.service;

import org.softuni.cardealer.domain.models.service.CarServiceModel;

import java.util.List;

public interface CarService {

    CarServiceModel saveCar(CarServiceModel carServiceModel);

    CarServiceModel editCar(String id, CarServiceModel carServiceModel);

    CarServiceModel deleteCar(String id);

    CarServiceModel findCarById(String id);

    List<CarServiceModel> findAll();

    void deletePartFromCars(String partId);
}
