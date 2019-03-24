package com.minkov.springintroapp.web;

import com.minkov.springintroapp.restModels.CarRequestModel;
import com.minkov.springintroapp.restModels.CarResponseModel;
import com.minkov.springintroapp.services.CarsService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CarsApiController {
    private final CarsService carsService;
    private final ModelMapper mapper;

    public CarsApiController(CarsService carsService, ModelMapper mapper) {
        this.carsService = carsService;
        this.mapper = mapper;
    }

    @GetMapping("/api/cars")
    public List<CarResponseModel> getCars() {
        return carsService.getAllCars()
                .stream()
                .map(car -> mapper.map(car, CarResponseModel.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/api/cars")
    public CarResponseModel create(CarRequestModel model) {
        // get user
        carsService.createCar(model.getModel(), "pesho@gosho.com");
        return null;
    }
}
