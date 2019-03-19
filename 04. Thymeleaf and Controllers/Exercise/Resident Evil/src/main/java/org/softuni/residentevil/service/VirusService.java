package org.softuni.residentevil.service;

import org.softuni.residentevil.domain.entities.Virus;
import org.softuni.residentevil.domain.models.binding.virus.VirusBindingModel;
import org.softuni.residentevil.domain.models.view.virus.VirusSimpleViewModel;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface VirusService extends Service<Virus, UUID> {

    List<VirusSimpleViewModel> getViruses();

    void createVirus(@NotNull VirusBindingModel virus);

    void updateVirus(@NotNull VirusBindingModel virus);

    void deleteVirus(@NotNull UUID id);
}
