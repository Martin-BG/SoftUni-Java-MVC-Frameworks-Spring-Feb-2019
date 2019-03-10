package org.softuni.exodia.web.controllers;

import org.softuni.exodia.annotations.AuthenticatedUser;
import org.softuni.exodia.annotations.Layout;
import org.softuni.exodia.config.WebConfig;
import org.softuni.exodia.domain.models.binding.user.UserLoginBindingModel;
import org.softuni.exodia.domain.models.binding.user.UserRegisterBindingModel;
import org.softuni.exodia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Layout
@AuthenticatedUser(authenticated = false)
@Controller
public class UserController extends BaseController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(WebConfig.URL_REGISTER)
    public String register() {
        return "register";
    }

    @PostMapping(WebConfig.URL_REGISTER)
    public String registerPost(@ModelAttribute UserRegisterBindingModel model) {
        if (service.register(model)) {
            return redirect(WebConfig.URL_LOGIN);
        }

        return redirect(WebConfig.URL_REGISTER);
    }

    @GetMapping(WebConfig.URL_LOGIN)
    public String login() {
        return "login";
    }

    @PostMapping(WebConfig.URL_LOGIN)
    public String loginPost(@ModelAttribute UserLoginBindingModel model, HttpSession session) {
        return service
                .login(model)
                .map(user -> {
                    session.setAttribute(WebConfig.SESSION_ATTRIBUTE_USERNAME, user.getUsername());
                    return redirect(WebConfig.URL_INDEX);
                })
                .orElse(redirect(WebConfig.URL_LOGIN));
    }

    @AuthenticatedUser
    @GetMapping(WebConfig.URL_LOGOUT)
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return redirect(WebConfig.URL_INDEX);
    }
}
