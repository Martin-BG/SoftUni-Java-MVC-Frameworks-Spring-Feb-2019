package com.minkov.springintroapp.services.implementations;

import com.minkov.springintroapp.dtos.CarDto;
import com.minkov.springintroapp.entities.Car;
import com.minkov.springintroapp.repositories.CarsRepository;
import com.minkov.springintroapp.services.CarValidationService;
import com.minkov.springintroapp.services.CarsFormatterService;
import com.minkov.springintroapp.services.CarsService;
import com.minkov.springintroapp.services.EmailsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarsServiceImpl implements CarsService {
    public static final String CAR_CREATED_EMAIL_SUBJECT = "You created new car";
    public static final String CAR_MODEL_CANNOT_BE_NULL_EXCEPTION_MESSAGE = "Car model cannot be null";

    private final CarsRepository carsRepository;
    private final EmailsService emailsService;
    private final CarsFormatterService carsFormatterService;
    private final ModelMapper mapper;
    private final CarValidationService carValidationService;

    public CarsServiceImpl(
            CarsRepository carsRepository,
            EmailsService emailsService,
            CarsFormatterService carsFormatterService,
            CarValidationService carValidationService,
            ModelMapper mapper) {
        this.carsRepository = carsRepository;
        this.emailsService = emailsService;
        this.carsFormatterService = carsFormatterService;
        this.carValidationService = carValidationService;
        this.mapper = mapper;
    }

    @Override
    public String[] getCarModels() {
        return carsRepository.findAll()
                .stream()
                .map(Car::getModel)
                .toArray(String[]::new);
    }

    @Override
    public Car[] getCarsByModel(String model) {
        return carsRepository.findAll()
                .stream()
                .filter(car -> car.getModel().equals(model))
                .toArray(Car[]::new);
    }

    @Override
    public List<CarDto> getAllCars() {
        return carsRepository.findAll()
                .stream()
                .map(car -> mapper.map(car, CarDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createCar(String carModel, String user) {
        // Validation

        Car car = new Car() {{
            setModel(carModel);
        }};

        if (!carValidationService.isCarValid(car)) {
            throw new NullPointerException(CAR_MODEL_CANNOT_BE_NULL_EXCEPTION_MESSAGE);
        }

        carsRepository.save(car);

        emailsService.send(
                user,
                CAR_CREATED_EMAIL_SUBJECT,
                carsFormatterService.getCarCreatedEmailBody(carModel));
    }
}
