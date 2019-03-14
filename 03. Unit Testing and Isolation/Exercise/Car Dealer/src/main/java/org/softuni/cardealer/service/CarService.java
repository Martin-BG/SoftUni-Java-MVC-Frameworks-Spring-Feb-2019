package org.softuni.cardealer.service;

import org.softuni.cardealer.domain.models.service.CarServiceModel;

public interface CarService {

    CarServiceModel saveCar(CarServiceModel carServiceModel);

    CarServiceModel editCar(CarServiceModel carServiceModel);

    CarServiceModel deleteCar(String id);

    CarServiceModel findCarById(String id);
}
