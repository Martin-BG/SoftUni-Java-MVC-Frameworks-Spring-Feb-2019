package org.softuni.residentevil.web.controllers.virus;

import org.softuni.residentevil.annotations.Layout;
import org.softuni.residentevil.service.VirusService;
import org.softuni.residentevil.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Layout
@Controller
@RequestMapping("/viruses/delete")
public class DeleteVirusController extends BaseController {

    private final VirusService service;

    @Autowired
    public DeleteVirusController(VirusService service) {
        this.service = service;
    }

    @DeleteMapping
    public String get(@RequestParam UUID id) {
        service.deleteById(id);
        return redirect("/viruses");
    }
}
