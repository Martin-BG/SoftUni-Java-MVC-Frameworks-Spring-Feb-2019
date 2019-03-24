package com.minkov.springintroapp.testutils;

import com.minkov.springintroapp.entities.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CarsUtils {
    // CarUtils.getCars();
    public static List<Car> getCars(int count) {
        return IntStream.range(0, count)
                .mapToObj(index -> new Car(){{
                    setId(index + 1);
                    setModel("Model " + index);
                }})
                .collect(Collectors.toList());
    }

    // carUtilsFactory
    // carUtilsFactory.getCarUtils().getCars();
}


