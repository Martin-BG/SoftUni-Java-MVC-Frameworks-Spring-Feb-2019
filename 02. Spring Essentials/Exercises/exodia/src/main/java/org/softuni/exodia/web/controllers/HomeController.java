package org.softuni.exodia.web.controllers;

import org.softuni.exodia.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController extends BaseController {

    private final DocumentService service;

    @Autowired
    public HomeController(DocumentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session != null && session.getAttribute("username") != null) {
            model.addAttribute("documents", service.findAllShortView());
            return buildView("home", model);
        } else {
            return buildView("index", model);
        }
    }
}
