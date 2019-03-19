package org.softuni.residentevil.web.controllers.virus;

import org.softuni.residentevil.domain.models.binding.virus.VirusBindingModel;
import org.softuni.residentevil.domain.models.view.capital.CapitalSimpleViewModel;
import org.softuni.residentevil.service.CapitalService;
import org.softuni.residentevil.service.VirusService;
import org.softuni.residentevil.web.annotations.Layout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;
import java.util.UUID;

@Layout
@Controller
@RequestMapping("/viruses/delete")
public class DeleteVirusController extends BaseVirusController {

    @Autowired
    public DeleteVirusController(VirusService virusService,
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
                    return "virus/delete";
                })
                .orElseThrow();
    }

    @DeleteMapping
    public String delete(@RequestParam UUID id,
                         SessionStatus sessionStatus) {
        sessionStatus.setComplete();

        virusService.deleteById(id);

        return redirect("/viruses");
    }
}
