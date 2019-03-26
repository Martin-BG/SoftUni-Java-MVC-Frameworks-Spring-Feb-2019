package org.softuni.residentevil.domain.models.projections.capital;

import org.softuni.residentevil.domain.api.Viewable;
import org.softuni.residentevil.domain.entities.Capital;

public interface CapitalAllProjection extends Viewable<Capital> {

    String getName();

    Double getLongitude();

    Double getLatitude();
}
