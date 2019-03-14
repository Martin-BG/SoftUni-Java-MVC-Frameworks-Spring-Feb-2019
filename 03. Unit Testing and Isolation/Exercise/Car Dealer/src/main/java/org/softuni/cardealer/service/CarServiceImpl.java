package org.softuni.cardealer.service;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CarServiceModel saveCar(CarServiceModel carServiceModel) {
        Car car = this.modelMapper.map(carServiceModel, Car.class);
        car = this.carRepository.saveAndFlush(car);

        return this.modelMapper.map(car, CarServiceModel.class);

    }

    @Override
    public CarServiceModel editCar(CarServiceModel carServiceModel) {
        Car car = this.carRepository.findById(carServiceModel.getId()).orElse(null);
        car.setMake(carServiceModel.getMake());
        car.setModel(carServiceModel.getModel());
        car.setTravelledDistance(carServiceModel.getTravelledDistance());

        car = this.carRepository.saveAndFlush(car);

        return this.modelMapper.map(car, CarServiceModel.class);

    }

    @Override
    public CarServiceModel deleteCar(String id) {
        Car car = this.carRepository.findById(id).orElse(null);

        this.carRepository.delete(car);

        return this.modelMapper.map(car, CarServiceModel.class);

    }

    @Override
    public CarServiceModel findCarById(String id) {
        Car car = this.carRepository.findById(id).orElse(null);

        return this.modelMapper.map(car, CarServiceModel.class);

    }
}
