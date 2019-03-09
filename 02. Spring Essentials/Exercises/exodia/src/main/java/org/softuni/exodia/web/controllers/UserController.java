package org.softuni.exodia.web.controllers;

import org.softuni.exodia.annotations.AuthenticatedUser;
import org.softuni.exodia.domain.models.binding.user.UserLoginBindingModel;
import org.softuni.exodia.domain.models.binding.user.UserRegisterBindingModel;
import org.softuni.exodia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@AuthenticatedUser(authenticated = false)
@Controller
public class UserController extends BaseController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String register(Model model) {
        return buildView("register", model);
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute UserRegisterBindingModel model) {
        if (service.register(model)) {
            return redirect("/login");
        }

        return redirect("/register");
    }

    @GetMapping("/login")
    public String login(Model model) {
        return buildView("login", model);
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute UserLoginBindingModel model, HttpSession session) {
        return service
                .login(model)
                .map(user -> {
                    session.setAttribute("username", user.getUsername());
                    return redirect("/");
                })
                .orElse(redirect("/login"));
    }

    @AuthenticatedUser
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return redirect("/");
    }
}
