package org.softuni.residentevil.web.controllers.virus;

import org.softuni.residentevil.annotations.Layout;
import org.softuni.residentevil.domain.models.binding.virus.VirusBindingModel;
import org.softuni.residentevil.domain.models.view.capital.CapitalSimpleViewModel;
import org.softuni.residentevil.service.CapitalService;
import org.softuni.residentevil.service.VirusService;
import org.softuni.residentevil.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Layout
@Controller
@RequestMapping("/viruses/add")
public class AddVirusController extends BaseController {

    private static final String VIRUS = "virus";
    private static final String CAPITALS = "capitals";

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
                       Errors errors,
                       Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(VIRUS, virus);
            return "virus/add";
        }

        virusService.create(virus);

        return redirect("/viruses");
    }
}
