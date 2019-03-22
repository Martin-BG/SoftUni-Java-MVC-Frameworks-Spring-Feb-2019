package org.softuni.cardealer.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.binding.AddCarBindingModel;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cars")
public class CarsController extends BaseController {
    private final CarService carService;

    private final ModelMapper modelMapper;

    @Autowired
    public CarsController(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/add")
    public ModelAndView addCar(@Valid @ModelAttribute AddCarBindingModel bindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //TODO: DO SOMETHING
        }

        CarServiceModel modelToSave = this.modelMapper.map(bindingModel, CarServiceModel.class);
        modelToSave.setParts(bindingModel.getParts().stream().map(x -> new PartServiceModel() {{setId(x);}}).collect(Collectors.toList()));

        this.carService.saveCar(modelToSave);

        return this.redirect("all");
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editCar(@PathVariable String id, @Valid @ModelAttribute AddCarBindingModel bindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //TODO: DO SOMETHING
        }

        this.carService.editCar(id, this.modelMapper.map(bindingModel, CarServiceModel.class));

        return this.redirect("/cars/all");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteCar(@PathVariable String id) {
        this.carService.deleteCar(id);

        return this.redirect("/cars/all");
    }

    @GetMapping("/all")
    public ModelAndView allCars(ModelAndView modelAndView) {
        modelAndView.addObject("cars", this.carService.findAll());

        return this.view("all-cars", modelAndView);
    }
}
