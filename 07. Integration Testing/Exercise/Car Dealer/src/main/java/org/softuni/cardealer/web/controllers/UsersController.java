package org.softuni.cardealer.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.binding.RegisterUserBindingModel;
import org.softuni.cardealer.domain.models.service.UserServiceModel;
import org.softuni.cardealer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController extends BaseController {
    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UsersController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return this.view("login");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return this.view("register");
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute RegisterUserBindingModel bindingModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            //TODO: DO SOMETHING
        }

        this.userService.saveUser(this.modelMapper.map(bindingModel, UserServiceModel.class));

        return this.redirect("login");
    }
}
