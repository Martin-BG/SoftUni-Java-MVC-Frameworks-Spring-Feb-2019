package org.softuni.exodia.web.controllers;

import org.softuni.exodia.annotations.Layout;
import org.softuni.exodia.config.WebConfig;
import org.softuni.exodia.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Layout
@Controller
public class HomeController extends BaseController {

    private final DocumentService service;

    @Autowired
    public HomeController(DocumentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session != null && session.getAttribute(WebConfig.SESSION_ATTRIBUTE_USERNAME) != null) {
            model.addAttribute("documents", service.findAllShortView());
            return "home";
        } else {
            return "index";
        }
    }
}
