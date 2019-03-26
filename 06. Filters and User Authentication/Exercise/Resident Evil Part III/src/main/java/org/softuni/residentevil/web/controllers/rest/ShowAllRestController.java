package org.softuni.residentevil.web.controllers.rest;

import org.softuni.residentevil.config.WebConfig;
import org.softuni.residentevil.domain.models.projections.capital.CapitalAllProjection;
import org.softuni.residentevil.domain.models.view.virus.VirusSimpleViewModel;
import org.softuni.residentevil.service.CapitalService;
import org.softuni.residentevil.service.VirusService;
import org.softuni.residentevil.web.annotations.Layout;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Layout
@Controller
public class ShowAllRestController {

    private static final String VIEW_COMMON_ALL = "common/all";

    private final VirusService virusService;
    private final CapitalService capitalService;

    public ShowAllRestController(VirusService virusService, CapitalService capitalService) {
        this.virusService = virusService;
        this.capitalService = capitalService;
    }

    @GetMapping(WebConfig.URL_ALL)
    public String get() {
        return VIEW_COMMON_ALL;
    }

    @ResponseBody
    @GetMapping(WebConfig.REST_API_CAPITAL)
    public List<CapitalAllProjection> capitals() {
        return capitalService.getCapitalsDetailed();
    }

    @ResponseBody
    @GetMapping(WebConfig.REST_API_VIRUS)
    public List<VirusSimpleViewModel> viruses() {
        return virusService.getViruses();
    }
}
