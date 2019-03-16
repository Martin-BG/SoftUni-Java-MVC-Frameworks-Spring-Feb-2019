package org.softuni.residentevil.domain.models.binding.capital;

import lombok.AllArgsConstructor;
import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.entities.Capital;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class CapitalBindingModel implements Bindable<Capital> {

    @NotNull
    Long id;
}
