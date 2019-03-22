package org.softuni.cardealer.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.binding.AddCarBindingModel;
import org.softuni.cardealer.domain.models.binding.AddCustomerBindingModel;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/customers")
public class CustomersController extends BaseController {
    private final CustomerService customerService;

    private final ModelMapper modelMapper;

    @Autowired
    public CustomersController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/add")
    public ModelAndView addCustomer(@Valid @ModelAttribute AddCustomerBindingModel bindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //TODO: DO SOMETHING
        }

        this.customerService.saveCustomer(this.modelMapper.map(bindingModel, CustomerServiceModel.class));

        return this.redirect("all");
    }

    @GetMapping("/all")
    public ModelAndView allCustomers(ModelAndView modelAndView) {
        modelAndView.addObject("customers", this.customerService.findAll());

        return this.view("all-customers", modelAndView);
    }
}
