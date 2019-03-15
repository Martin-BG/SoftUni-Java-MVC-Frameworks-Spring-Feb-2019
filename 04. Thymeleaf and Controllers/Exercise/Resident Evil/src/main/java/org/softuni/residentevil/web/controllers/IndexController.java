package org.softuni.residentevil.web.controllers;

import org.softuni.residentevil.annotations.Layout;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Layout
@Controller
public class IndexController extends BaseController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
