package com.minkov.springintroapp.web;

import com.minkov.springintroapp.services.CarsService;
import com.minkov.springintroapp.viewmodels.CarViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CarsController {
    private final CarsService carsService;
    private final ModelMapper mapper;

    public CarsController(
            CarsService carsService,
            ModelMapper mapper
    ) {
        this.carsService = carsService;
        this.mapper = mapper;
    }

    @GetMapping("/cars")
    public String getAllCars(Model model) {
        List<CarViewModel> cars = carsService.getAllCars()
                .stream()
                .map(car -> mapper.map(car, CarViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("viewModel", cars);
        return "cars/all";
    }
}
