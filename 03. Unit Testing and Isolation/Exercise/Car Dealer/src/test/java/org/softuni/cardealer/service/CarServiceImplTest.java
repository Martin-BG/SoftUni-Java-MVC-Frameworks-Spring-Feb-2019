package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    public void saveCar_validInputData_correctMethodsAndArgumentsUsed() {
        CarServiceModel model = mock(CarServiceModel.class);
        Car car = mock(Car.class);
        Mockito.when(modelMapper.map(model, Car.class)).thenReturn(car);
        Mockito.when(carRepository.saveAndFlush(car)).thenReturn(car);
        Mockito.when(modelMapper.map(car, CarServiceModel.class)).thenReturn(model);

        CarServiceModel result = carService.saveCar(model);

        Mockito.verify(modelMapper).map(model, Car.class);
        Mockito.verify(carRepository).saveAndFlush(car);
        Mockito.verify(modelMapper).map(car, CarServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCar_nullInput_throwsIllegalArgumentException() {
        Mockito.when(modelMapper.map(eq(null), any()))
                .thenThrow(new IllegalArgumentException());

        carService.saveCar(null);
    }

    @Test
    public void editCar_validInputData_correctMethodsAndArgumentsUsed() {
        Car car = mock(Car.class);
        CarServiceModel model = mock(CarServiceModel.class);
        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(model.getMake()).thenReturn("make");
        Mockito.when(model.getModel()).thenReturn("model");
        Mockito.when(model.getTravelledDistance()).thenReturn(1L);
        Mockito.when(carRepository.findById(eq("id"))).thenReturn(Optional.of(car));
        Mockito.when(carRepository.saveAndFlush(car)).thenReturn(car);
        Mockito.when(modelMapper.map(car, CarServiceModel.class)).thenReturn(model);

        CarServiceModel result = carService.editCar(model);

        Mockito.verify(carRepository).findById("id");
        Mockito.verify(car).setMake("make");
        Mockito.verify(car).setModel("model");
        Mockito.verify(car).setTravelledDistance(1L);
        Mockito.verify(carRepository).saveAndFlush(car);
        Mockito.verify(modelMapper).map(car, CarServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = NullPointerException.class)
    public void editCar_invalidId_throwsNullPointerException() {
        Mockito.when(carRepository.findById(any())).thenReturn(Optional.empty());

        carService.editCar(new CarServiceModel());
    }

    @Test(expected = NullPointerException.class)
    public void editCar_nullInput_throwsNullPointerException() {
        carService.editCar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCar_nullInput_throwsIllegalArgumentException() {
        Mockito.when(carRepository.findById(null))
                .thenThrow(new IllegalArgumentException());

        carService.deleteCar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCar_invalidId_throwsIllegalArgumentException() {
        Mockito.when(carRepository.findById("id"))
                .thenReturn(Optional.empty());
        doThrow(IllegalArgumentException.class)
                .when(carRepository)
                .delete(null);

        carService.deleteCar("id");
    }

    @Test
    public void deleteCar_validId_correctMethodsAndArgumentsUsed() {
        Car car = mock(Car.class);
        CarServiceModel model = mock(CarServiceModel.class);
        Mockito.when(carRepository.findById("id")).thenReturn(Optional.of(car));
        Mockito.when(modelMapper.map(car, CarServiceModel.class)).thenReturn(model);

        CarServiceModel result = carService.deleteCar("id");

        Mockito.verify(carRepository).findById("id");
        Mockito.verify(carRepository).delete(car);
        Mockito.verify(modelMapper).map(car, CarServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findCarById_nullInput_throwsIllegalArgumentException() {
        Mockito.when(carRepository.findById(null))
                .thenThrow(new IllegalArgumentException());

        carService.findCarById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findCarById_invalidId_throwsIllegalArgumentException() {
        Mockito.when(carRepository.findById("id"))
                .thenReturn(Optional.empty());

        Mockito.when(modelMapper.map(null, CarServiceModel.class))
                .thenThrow(new IllegalArgumentException());

        carService.findCarById("id");
    }

    @Test
    public void findCarById_validId_correctMethodsAndArgumentsUsed() {
        Car car = mock(Car.class);
        CarServiceModel model = mock(CarServiceModel.class);
        Mockito.when(carRepository.findById("id")).thenReturn(Optional.of(car));
        Mockito.when(modelMapper.map(car, CarServiceModel.class)).thenReturn(model);

        CarServiceModel result = carService.findCarById("id");

        Mockito.verify(carRepository).findById("id");
        Mockito.verify(modelMapper).map(car, CarServiceModel.class);
        Assert.assertEquals(model, result);
    }
}
