package org.softuni.residentevil.web.controllers.virus;

import org.softuni.residentevil.config.WebConfig;
import org.softuni.residentevil.domain.models.binding.virus.VirusBindingModel;
import org.softuni.residentevil.domain.models.view.capital.CapitalSimpleViewModel;
import org.softuni.residentevil.service.CapitalService;
import org.softuni.residentevil.service.VirusService;
import org.softuni.residentevil.web.annotations.Layout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Layout
@Controller
@RequestMapping(WebConfig.URL_VIRUS_EDIT)
public class EditVirusController extends BaseVirusController {

    private static final String VIEW_VIRUS_EDIT = "virus/edit";

    @Autowired
    public EditVirusController(VirusService virusService,
                               CapitalService capitalService) {
        super(virusService, capitalService);
    }

    @ModelAttribute(CAPITALS)
    public List<CapitalSimpleViewModel> capitalSimpleViewModelList() {
        return capitalService.getCapitals();
    }

    @PostMapping
    public String post(@RequestParam UUID id, Model model) {
        return virusService
                .readVirus(id)
                .map(virus -> {
                    model.addAttribute(VIRUS, virus);
                    return VIEW_VIRUS_EDIT;
                })
                .orElseThrow();
    }

    @PutMapping
    public String put(@Valid @ModelAttribute(VIRUS) VirusBindingModel virus,
                      Errors errors,
                      SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return VIEW_VIRUS_EDIT;
        }

        virusService.updateVirus(virus);

        sessionStatus.setComplete();

        return redirect(WebConfig.URL_VIRUS_ALL);
    }
}
