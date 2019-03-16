package org.softuni.residentevil.domain.models.binding.virus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.residentevil.annotations.composite.virus.*;
import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.entities.Virus;
import org.softuni.residentevil.domain.enums.Creator;
import org.softuni.residentevil.domain.enums.Magnitude;
import org.softuni.residentevil.domain.enums.Mutation;
import org.softuni.residentevil.domain.models.binding.capital.CapitalBindingModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VirusBindingModel implements Bindable<Virus> {

    private String id;

    @ValidVirusName
    private String name;

    @ValidVirusDescription
    private String description;

    @ValidVirusSideEffects
    private String sideEffects;

    @ValidVirusCreator
    private Creator creator;

    @ValidVirusIsDeadly
    private Boolean isDeadly;

    @ValidVirusIsCurable
    private Boolean isCurable;

    @ValidVirusMutation
    private Mutation mutation;

    @ValidVirusTurnoverRate
    private Integer turnoverRate;

    @ValidVirusHoursUntilMutation
    private Integer hoursUntilMutation;

    @ValidVirusMagnitude
    private Magnitude magnitude;

    @ValidVirusReleasedOn
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releasedOn;

    @ValidVirusCapitals
    private List<@Valid CapitalBindingModel> capitals = new ArrayList<>();
}
