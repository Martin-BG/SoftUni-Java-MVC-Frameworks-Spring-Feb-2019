package org.softuni.cardealer.service;

import org.softuni.cardealer.domain.models.service.PartServiceModel;

import java.util.List;

public interface PartService {

    PartServiceModel savePart(PartServiceModel partServiceModel);

    PartServiceModel editPart(String id, PartServiceModel partServiceModel);

    PartServiceModel deletePart(String id);

    PartServiceModel findPartById(String id);

    List<PartServiceModel> findAll();
}
