package org.softuni.realestate.web.controllers;

import org.softuni.realestate.domain.models.binding.OfferFindBindingModel;
import org.softuni.realestate.domain.models.binding.OfferRegisterBindingModel;
import org.softuni.realestate.domain.models.view.OfferViewModel;
import org.softuni.realestate.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class OfferController {

    private final OfferService service;

    public OfferController(OfferService service) {
        this.service = service;
    }

    private static ModelAndView view(String viewName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    private static ModelAndView redirect(String viewName) {
        return view("redirect:" + viewName);
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return view("register");
    }

    @PostMapping("/register")
    public ModelAndView registerPost(OfferRegisterBindingModel model) {
        if (service.registerOffer(model)) {
            return redirect("/");
        }
        return redirect("/register");
    }

    @GetMapping("/find")
    public ModelAndView find() {
        return view("find");
    }

    @PostMapping("/find")
    public ModelAndView findPost(@Valid OfferFindBindingModel model) {
        if (service.findOffer(model, OfferViewModel.class).isPresent()) {
            return redirect("/");
        }
        return redirect("/find");
    }
}
