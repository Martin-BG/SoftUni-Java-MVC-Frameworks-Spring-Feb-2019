package com.minkov.springintroapp.web;

import com.minkov.springintroapp.viewmodels.WhiskeyModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class WhiskeyController {
    @GetMapping("/dates")
    public ModelAndView getDate(ModelAndView modelAndView) {
        Date today = new Date();
        Date tomorrow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));

        modelAndView.addObject("dates", new Date[]{
                today,
                tomorrow,
        });
        modelAndView.addObject("date", today);
        modelAndView.setViewName("dates");

        modelAndView.addObject("numbers", new double[]{1.1, 2.2, 3.3,});

        // get UserName
        modelAndView.addObject("username", "Doncho");

        return modelAndView;
    }


    @ModelAttribute("whiskey")
    public WhiskeyModel createStudentModel() {
        return new WhiskeyModel();
    }

    @GetMapping("/whiskey")
    public ModelAndView getWhiskeyForm() {
        return new ModelAndView("add", "whiskey", new WhiskeyModel());
    }
}
