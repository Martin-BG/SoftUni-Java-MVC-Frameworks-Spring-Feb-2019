package org.softuni.cardealer.service;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.softuni.cardealer.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    private final PartRepository partRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CarServiceModel saveCar(CarServiceModel carServiceModel) {
        Car car = this.modelMapper.map(carServiceModel, Car.class);
        car.setParts(carServiceModel.getParts().stream()
                .map(x -> this.partRepository.findById(x.getId()).orElse(null))
                .collect(Collectors.toList()));
        car = this.carRepository.saveAndFlush(car);

        return this.modelMapper.map(car, CarServiceModel.class);

    }

    @Override
    public CarServiceModel editCar(String id, CarServiceModel carServiceModel) {
        Car car = this.carRepository.findById(id).orElse(null);
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

    @Override
    public List<CarServiceModel> findAll() {
        return this.carRepository.findAll()
                .stream()
                .map(x -> this.modelMapper.map(x, CarServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePartFromCars(String partId) {
        this.carRepository.findAll()
                .stream()
                .filter(x -> x.getParts().stream().anyMatch(y -> y.getId().equals(partId)))
                .forEach((x) -> {
                    List<Part> carParts = x.getParts();
                    carParts.removeIf(y -> y.getId().equals(partId));
                    x.setParts(carParts);
                    this.carRepository.saveAndFlush(x);
                });
    }
}
