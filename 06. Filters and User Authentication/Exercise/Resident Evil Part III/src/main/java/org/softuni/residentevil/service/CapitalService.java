package org.softuni.residentevil.service;

import org.softuni.residentevil.domain.entities.Capital;
import org.softuni.residentevil.domain.models.projections.capital.CapitalAllProjection;
import org.softuni.residentevil.domain.models.view.capital.CapitalSimpleViewModel;

import java.util.List;

public interface CapitalService extends Service<Capital, Long> {

    List<CapitalSimpleViewModel> getCapitals();

    List<CapitalAllProjection> getCapitalsDetailed();
}
