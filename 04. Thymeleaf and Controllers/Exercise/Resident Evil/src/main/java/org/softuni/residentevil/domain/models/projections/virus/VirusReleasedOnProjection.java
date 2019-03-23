package org.softuni.residentevil.domain.models.projections.virus;

import org.softuni.residentevil.domain.api.Viewable;
import org.softuni.residentevil.domain.entities.Virus;

import java.util.Date;

public interface VirusReleasedOnProjection extends Viewable<Virus> {

    Date getReleasedOn();
}
