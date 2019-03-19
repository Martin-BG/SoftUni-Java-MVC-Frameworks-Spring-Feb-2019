package org.softuni.residentevil.web.controllers.virus;

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
@RequestMapping("/viruses/edit")
public class EditVirusController extends BaseVirusController {

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
                .findById(id, VirusBindingModel.class)
                .map(virus -> {
                    model.addAttribute(VIRUS, virus);
                    return "virus/edit";
                })
                .orElseThrow();
    }

    @PutMapping
    public String put(@Valid @ModelAttribute(VIRUS) VirusBindingModel virus,
                      Errors errors,
                      SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "virus/edit";
        }

        virusService.updateVirus(virus);

        sessionStatus.setComplete();

        return redirect("/viruses");
    }
}
