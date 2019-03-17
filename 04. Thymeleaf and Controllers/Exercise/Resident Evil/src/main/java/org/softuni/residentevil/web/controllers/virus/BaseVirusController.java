package org.softuni.residentevil.web.controllers.virus;

import org.softuni.residentevil.service.CapitalService;
import org.softuni.residentevil.service.VirusService;
import org.softuni.residentevil.web.controllers.BaseController;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes({BaseVirusController.VIRUS, BaseVirusController.CAPITALS})
class BaseVirusController extends BaseController {

    public static final String VIRUS = "virus";
    public static final String CAPITALS = "allCapitals";

    protected final VirusService virusService;
    protected final CapitalService capitalService;

    protected BaseVirusController(VirusService virusService,
                                  CapitalService capitalService) {
        this.virusService = virusService;
        this.capitalService = capitalService;
    }
}
