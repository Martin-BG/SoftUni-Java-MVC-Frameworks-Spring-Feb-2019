package org.softuni.residentevil.domain.models.binding.capital;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.entities.Capital;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class CapitalBindingModel implements Bindable<Capital>, Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    Long id;
}
