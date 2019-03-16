package org.softuni.residentevil.domain.models.view.virus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.residentevil.domain.api.Viewable;
import org.softuni.residentevil.domain.entities.Virus;
import org.softuni.residentevil.domain.enums.Magnitude;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public final class VirusSimpleViewModel implements Viewable<Virus> {

    private String id;

    private String name;

    private Magnitude magnitude;

    private LocalDate releasedOn;
}
