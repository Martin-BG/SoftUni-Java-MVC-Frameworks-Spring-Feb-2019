package org.softuni.cardealer.service;

import org.softuni.cardealer.domain.models.service.PartServiceModel;

public interface PartService {

    PartServiceModel savePart(PartServiceModel partServiceModel);

    PartServiceModel editPart(PartServiceModel partServiceModel);

    PartServiceModel deletePart(String id);

    PartServiceModel findPartById(String id);
}
