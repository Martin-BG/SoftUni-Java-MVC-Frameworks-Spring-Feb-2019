package org.softuni.residentevil.web.controllers.virus;

import org.softuni.residentevil.annotations.Layout;
import org.softuni.residentevil.service.VirusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Layout
@Controller
@RequestMapping("/viruses")
public class ShowVirusController {

    private final VirusService virusService;

    public ShowVirusController(VirusService virusService) {
        this.virusService = virusService;
    }

    @GetMapping
    public String get(Model model) {
        model.addAttribute("viruses", virusService.getViruses());

        return "virus/all";
    }
}
