package org.softuni.cardealer.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.binding.AddSupplierBindingModel;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/suppliers")
public class SuppliersController extends BaseController {
    private final SupplierService supplierService;

    private final ModelMapper modelMapper;

    @Autowired
    public SuppliersController(SupplierService supplierService, ModelMapper modelMapper) {
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ModelAndView addSupplier(@Valid @ModelAttribute AddSupplierBindingModel bindingModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            //TODO: DO SOMETHING
        }

        this.supplierService.saveSupplier(this.modelMapper.map(bindingModel, SupplierServiceModel.class));

        return this.redirect("all");
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editSupplier(@PathVariable String id, @Valid @ModelAttribute AddSupplierBindingModel bindingModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            //TODO: DO SOMETHING
        }

        this.supplierService.editSupplier(id, this.modelMapper.map(bindingModel, SupplierServiceModel.class));

        return this.redirect("/suppliers/all");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteSupplier(@PathVariable String id) {
        this.supplierService.deleteSupplier(id);

        return this.redirect("/suppliers/all");
    }

    @GetMapping("/all")
    public ModelAndView allSuppliers(ModelAndView modelAndView) {
        modelAndView.addObject("suppliers", this.supplierService.findAll());

        return this.view("all-suppliers", modelAndView);
    }

    @GetMapping("/fetch")
    @ResponseBody
    public List<SupplierServiceModel> fetchSuppliers() {
        return this.supplierService.findAll();
    }
}
