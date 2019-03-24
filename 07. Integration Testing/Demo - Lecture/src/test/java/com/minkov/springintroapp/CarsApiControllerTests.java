package com.minkov.springintroapp;

import com.minkov.springintroapp.entities.Car;
import com.minkov.springintroapp.repositories.CarsRepository;
import com.minkov.springintroapp.restModels.CarRequestModel;
import com.minkov.springintroapp.restModels.CarResponseModel;
import com.minkov.springintroapp.services.CarValidationService;
import com.minkov.springintroapp.services.CarsFormatterService;
import com.minkov.springintroapp.services.EmailsService;
import com.minkov.springintroapp.services.implementations.CarsServiceImpl;
import com.minkov.springintroapp.testutils.CarsUtils;
import com.minkov.springintroapp.web.CarsApiController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CarsApiControllerTests {
    List<Car> cars;

    @MockBean
    CarsRepository mockCarsRepository;
    @MockBean
    EmailsService mockEmailsService;
    @MockBean
    CarsFormatterService mockCarsFormatterService;

    @Mock
    CarValidationService mockCarValidationService;

    @Autowired
    CarsApiController controller;

    @Before
    public void setupTest() {
        cars = new ArrayList<>();
        when(mockCarsRepository.findAll())
                .thenReturn(cars);
    }

    @Test
    public void getAllCars_WhenNoCars_EmptyList() {
        cars.clear();
        List<CarResponseModel> result = controller.getCars();
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllCars_When2Cars_2Cars() {
        cars.clear();
        cars.addAll(CarsUtils.getCars(2));
        List<CarResponseModel> result = controller.getCars();
        assertEquals(2, result.size());
    }

    @Test
    public void create_whenCarAndUserAreValid_createCarAndSendEmail() {
        String carModel = "CAR MODEL";
        when(mockCarsFormatterService.getCarCreatedEmailBody(any()))
                .thenReturn("uha");

        when(mockCarValidationService.isCarValid(any()))
                .thenReturn(true);

        when(mockCarsRepository.save(any()))
                .thenAnswer((Answer) invocation -> {
                    Object[] args = invocation.getArguments();
                    Car car = (Car) args[0];
                    Assert.assertEquals(car.getModel(), carModel);

                    return args[0];
                });

        // Act
        CarRequestModel carRequestModel = new CarRequestModel() {{
            setModel(carModel);
        }};

        controller.create(carRequestModel);

        // Assert

        verify(mockEmailsService).send(
                "pesho@gosho.com",
                CarsServiceImpl.CAR_CREATED_EMAIL_SUBJECT,
                "uha");
    }
}
