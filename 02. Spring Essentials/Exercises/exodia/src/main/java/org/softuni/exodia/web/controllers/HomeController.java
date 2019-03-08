package org.softuni.exodia.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(HttpSession session) {
        if (session != null && session.getAttribute("username") != null) {
            return "home";
        }
        return "index";
    }
}
