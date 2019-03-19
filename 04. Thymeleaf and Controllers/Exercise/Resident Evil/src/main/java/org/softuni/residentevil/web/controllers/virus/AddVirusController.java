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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@Layout
@Controller
@RequestMapping("/viruses/add")
public class AddVirusController extends BaseVirusController {

    @Autowired
    public AddVirusController(VirusService virusService,
                              CapitalService capitalService) {
        super(virusService, capitalService);
    }

    @ModelAttribute(CAPITALS)
    public List<CapitalSimpleViewModel> capitalSimpleViewModelList() {
        return capitalService.getCapitals();
    }

    @GetMapping
    public String get(Model model) {
        model.addAttribute(VIRUS, new VirusBindingModel());

        return "virus/add";
    }

    @PostMapping
    public String post(@Valid @ModelAttribute(VIRUS) VirusBindingModel virus,
                       Errors errors, SessionStatus sessionStatus) {
        if (virus.getId() == null) {
            if (errors.hasErrors()) {
                return "virus/add";
            }
            virusService.create(virus);
        }

        sessionStatus.setComplete();

        return redirect("/viruses");
    }
}
