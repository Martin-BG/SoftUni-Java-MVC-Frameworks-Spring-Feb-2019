package org.softuni.residentevil.web.controllers.virus;

import org.softuni.residentevil.annotations.Layout;
import org.softuni.residentevil.domain.models.binding.virus.VirusBindingModel;
import org.softuni.residentevil.domain.models.view.capital.CapitalSimpleViewModel;
import org.softuni.residentevil.service.CapitalService;
import org.softuni.residentevil.service.VirusService;
import org.softuni.residentevil.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@Layout
@Controller
@RequestMapping("/viruses/add")
@SessionAttributes({AddVirusController.VIRUS, AddVirusController.CAPITALS})
public class AddVirusController extends BaseController {

    public static final String VIRUS = "virus";
    public static final String CAPITALS = "allCapitals";

    private final VirusService virusService;
    private final CapitalService capitalService;

    @Autowired
    public AddVirusController(VirusService virusService,
                              CapitalService capitalService) {
        this.virusService = virusService;
        this.capitalService = capitalService;
    }

    @ModelAttribute(VIRUS)
    public VirusBindingModel virusBindingModel() {
        return new VirusBindingModel();
    }

    @ModelAttribute(CAPITALS)
    public List<CapitalSimpleViewModel> capitalSimpleViewModelList() {
        return capitalService.getCapitals();
    }

    @GetMapping
    public String get() {
        return "virus/add";
    }

    @PostMapping
    public String post(@Valid @ModelAttribute(VIRUS) VirusBindingModel virus,
                       Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors() || !virusService.create(virus)) {
            return "virus/add";
        }

        sessionStatus.setComplete();

        return redirect("/viruses");
    }
}
