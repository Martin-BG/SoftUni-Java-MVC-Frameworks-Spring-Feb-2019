package com.minkov.springintroapp;

import com.minkov.springintroapp.entities.Car;
import com.minkov.springintroapp.repositories.CarsRepository;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarsServiceImplTests {
    @Mock
    private CarsRepository mockCarsRepository;

    public void test1() {
        String model = "model";
        List<Car> cars = List.of(
                new Car() {{
                    setId(1);
                    setModel(model);
                }},
                new Car() {{
                    setId(1);
                    setModel(model);
                }}
        );
        when(mockCarsRepository.findAllByModel(anyString()))
                .thenReturn(cars);


    }
}
