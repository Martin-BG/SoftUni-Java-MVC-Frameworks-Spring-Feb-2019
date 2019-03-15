package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
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
public class CarServiceTests {

    @Mock
    private CarRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CarServiceImpl service;

    @Before
    public void initTests() {
        Mockito.when(modelMapper.map(eq(null), any()))
                .thenThrow(new IllegalArgumentException());
    }

    @Test
    public void saveCar_validInputData_correctMethodsAndArgumentsUsed() {
        Car car = mock(Car.class);
        CarServiceModel model = mock(CarServiceModel.class);
        Mockito.when(modelMapper.map(model, Car.class)).thenReturn(car);
        Mockito.when(repository.saveAndFlush(car)).thenReturn(car);
        Mockito.when(modelMapper.map(car, CarServiceModel.class)).thenReturn(model);

        CarServiceModel result = service.saveCar(model);

        Mockito.verify(modelMapper).map(model, Car.class);
        Mockito.verify(repository).saveAndFlush(car);
        Mockito.verify(modelMapper).map(car, CarServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCar_nullInput_throwsIllegalArgumentException() {
        service.saveCar(null);
    }

    @Test
    public void editCar_validInputData_correctMethodsAndArgumentsUsed() {
        Car car = mock(Car.class);
        CarServiceModel model = mock(CarServiceModel.class);
        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(model.getMake()).thenReturn("make");
        Mockito.when(model.getModel()).thenReturn("model");
        Mockito.when(model.getTravelledDistance()).thenReturn(1L);
        Mockito.when(repository.findById(eq("id"))).thenReturn(Optional.of(car));
        Mockito.when(repository.saveAndFlush(car)).thenReturn(car);
        Mockito.when(modelMapper.map(car, CarServiceModel.class)).thenReturn(model);

        CarServiceModel result = service.editCar(model);

        Mockito.verify(repository).findById("id");
        Mockito.verify(car).setMake("make");
        Mockito.verify(car).setModel("model");
        Mockito.verify(car).setTravelledDistance(1L);
        Mockito.verify(repository).saveAndFlush(car);
        Mockito.verify(modelMapper).map(car, CarServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = NullPointerException.class)
    public void editCar_invalidId_throwsNullPointerException() {
        CarServiceModel model = mock(CarServiceModel.class);
        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(repository.findById("id")).thenReturn(Optional.empty());

        service.editCar(model);
    }

    @Test(expected = NullPointerException.class)
    public void editCar_nullInput_throwsNullPointerException() {
        service.editCar(null);
    }

    @Test
    public void deleteCar_validId_correctMethodsAndArgumentsUsed() {
        Car car = mock(Car.class);
        CarServiceModel model = mock(CarServiceModel.class);
        Mockito.when(repository.findById("id")).thenReturn(Optional.of(car));
        Mockito.when(modelMapper.map(car, CarServiceModel.class)).thenReturn(model);

        CarServiceModel result = service.deleteCar("id");

        Mockito.verify(repository).findById("id");
        Mockito.verify(repository).delete(car);
        Mockito.verify(modelMapper).map(car, CarServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCar_nullInput_throwsIllegalArgumentException() {
        Mockito.when(repository.findById(null))
                .thenThrow(new IllegalArgumentException());

        service.deleteCar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCar_invalidId_throwsIllegalArgumentException() {
        Mockito.when(repository.findById("id"))
                .thenReturn(Optional.empty());
        doThrow(IllegalArgumentException.class)
                .when(repository)
                .delete(null);

        service.deleteCar("id");
    }

    @Test
    public void findCarById_validId_correctMethodsAndArgumentsUsed() {
        Car car = mock(Car.class);
        CarServiceModel model = mock(CarServiceModel.class);
        Mockito.when(repository.findById("id")).thenReturn(Optional.of(car));
        Mockito.when(modelMapper.map(car, CarServiceModel.class)).thenReturn(model);

        CarServiceModel result = service.findCarById("id");

        Mockito.verify(repository).findById("id");
        Mockito.verify(modelMapper).map(car, CarServiceModel.class);
        Assert.assertEquals(model, result);
    }


    @Test(expected = IllegalArgumentException.class)
    public void findCarById_nullInput_throwsIllegalArgumentException() {
        Mockito.when(repository.findById(null))
                .thenThrow(new IllegalArgumentException());

        service.findCarById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findCarById_invalidId_throwsIllegalArgumentException() {
        Mockito.when(repository.findById("id"))
                .thenReturn(Optional.empty());

        service.findCarById("id");
    }
}
