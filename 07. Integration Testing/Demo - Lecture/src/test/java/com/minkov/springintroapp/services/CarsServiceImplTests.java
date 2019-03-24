package com.minkov.springintroapp.services;

import com.minkov.springintroapp.entities.Car;
import com.minkov.springintroapp.repositories.CarsRepository;
import com.minkov.springintroapp.services.implementations.CarsServiceImpl;
import com.minkov.springintroapp.testutils.CarsUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarsServiceImplTests {
    List<Car> cars;

    @Mock
    CarsRepository mockRepository;

    ModelMapper mapper;

    CarsServiceImpl service;

    @Before
    public void setup() {
        cars = new ArrayList<>();

        when(mockRepository.findAll())
                .thenReturn(cars);
//        service = new CarsServiceImpl(mockRepository, mapper);
    }

    @Test
    public void getCarModels_when2Cars_2CarModels() {
        cars.addAll(CarsUtils.getCars(2));

        // Act (do the behavior)
        String[] carModels = service.getCarModels();

        // Assert (check if correct)
        assertThat(carModels.length, is(2));
    }

    @Test
    public void getCarModels_whenNoCars_emptyArray() {
        // Arrange (setup)
        cars.addAll(CarsUtils.getCars(0));
        // Act (do the behavior)
        String[] carModels = service.getCarModels();

        // Assert (check if correct)
        assertThat(carModels.length, is(0));
    }
}
