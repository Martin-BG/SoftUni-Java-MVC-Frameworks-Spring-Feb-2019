package org.softuni.residentevil.web.controllers.virus;

import org.softuni.residentevil.config.WebConfig;
import org.softuni.residentevil.service.VirusService;
import org.softuni.residentevil.web.annotations.Layout;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Layout
@Controller
@RequestMapping(WebConfig.URL_VIRUS_ALL)
public class ShowVirusController {

    private static final String VIEW_VIRUS_ALL = "virus/all";

    private final VirusService virusService;

    public ShowVirusController(VirusService virusService) {
        this.virusService = virusService;
    }

    @GetMapping
    public String get(Model model) {
        model.addAttribute(BaseVirusController.VIRUSES, virusService.getViruses());

        return VIEW_VIRUS_ALL;
    }
}
