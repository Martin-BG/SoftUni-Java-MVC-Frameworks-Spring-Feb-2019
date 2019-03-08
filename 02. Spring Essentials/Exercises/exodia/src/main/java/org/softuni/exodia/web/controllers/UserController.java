package org.softuni.exodia.web.controllers;

import org.softuni.exodia.domain.models.binding.user.UserLoginBindingModel;
import org.softuni.exodia.domain.models.binding.user.UserRegisterBindingModel;
import org.softuni.exodia.domain.models.view.user.UserLoggedViewModel;
import org.softuni.exodia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute UserRegisterBindingModel model) {
        if (service.register(model)) {
            return "redirect:/login";
        }

        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute UserLoginBindingModel model, HttpSession session) {
        Optional<UserLoggedViewModel> user = service.login(model);
        if (user.isPresent()) {
            session.setAttribute("username", user.get().getUsername());
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
