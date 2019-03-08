package org.softuni.exodia.web.controllers;

import org.softuni.exodia.domain.models.view.document.DocumentTitleAndIdViewModel;
import org.softuni.exodia.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    private final DocumentService service;

    @Autowired
    public HomeController(DocumentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session != null && session.getAttribute("username") != null) {
            List<DocumentTitleAndIdViewModel> documents = service.findAllShortView();
            model.addAttribute("documents", documents);
            model.addAttribute("view", "views/home.html");
        } else {
            model.addAttribute("view", "views/index.html");
        }
        return "base-layout";
    }
}
