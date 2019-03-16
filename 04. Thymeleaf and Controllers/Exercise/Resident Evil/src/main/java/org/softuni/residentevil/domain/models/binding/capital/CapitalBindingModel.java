package org.softuni.residentevil.domain.models.binding.capital;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.entities.Capital;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class CapitalBindingModel implements Bindable<Capital> {

    @NotNull
    Long id;
}
