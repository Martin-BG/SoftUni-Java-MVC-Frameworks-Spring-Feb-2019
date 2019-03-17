package org.softuni.residentevil.service;

import org.softuni.residentevil.domain.entities.Virus;
import org.softuni.residentevil.domain.models.view.virus.VirusSimpleViewModel;

import java.util.List;
import java.util.UUID;

public interface VirusService extends Service<Virus, UUID> {

    List<VirusSimpleViewModel> getViruses();
}
