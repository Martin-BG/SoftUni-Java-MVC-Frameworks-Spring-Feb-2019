package org.softuni.cardealer.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.binding.AddPartBindingModel;
import org.softuni.cardealer.domain.models.binding.AddSupplierBindingModel;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.service.PartService;
import org.softuni.cardealer.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/parts")
public class PartsController extends BaseController {
    private final PartService partService;

    private final ModelMapper modelMapper;

    @Autowired
    public PartsController(PartService partService, ModelMapper modelMapper) {
        this.partService = partService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ModelAndView addPart(@Valid @ModelAttribute AddPartBindingModel bindingModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            //TODO: DO SOMETHING
        }

        PartServiceModel modelToSave = this.modelMapper.map(bindingModel, PartServiceModel.class);
        modelToSave.setSupplier(new SupplierServiceModel(){{
            setName(bindingModel.getSupplier());
        }});

        this.partService.savePart(modelToSave);

        return this.redirect("all");
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editPart(@PathVariable String id, @Valid @ModelAttribute AddPartBindingModel bindingModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            //TODO: DO SOMETHING
        }

        this.partService.editPart(id, this.modelMapper.map(bindingModel, PartServiceModel.class));

        return this.redirect("/parts/all");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deletePart(@PathVariable String id) {
        this.partService.deletePart(id);

        return this.redirect("/parts/all");
    }

    @GetMapping("/all")
    public ModelAndView allParts(ModelAndView modelAndView) {
        modelAndView.addObject("parts", this.partService.findAll());

        return this.view("all-parts", modelAndView);
    }

    @GetMapping("/fetch")
    @ResponseBody
    public List<PartServiceModel> fetchParts() {
        return this.partService.findAll();
    }
}
