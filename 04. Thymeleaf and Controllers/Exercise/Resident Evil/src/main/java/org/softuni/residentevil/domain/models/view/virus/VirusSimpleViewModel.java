package org.softuni.residentevil.domain.models.view.virus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.residentevil.domain.api.Viewable;
import org.softuni.residentevil.domain.entities.Virus;
import org.softuni.residentevil.domain.enums.Magnitude;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class VirusSimpleViewModel implements Viewable<Virus>, Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;

    private String name;

    private Magnitude magnitude;

    private LocalDate releasedOn;
}
