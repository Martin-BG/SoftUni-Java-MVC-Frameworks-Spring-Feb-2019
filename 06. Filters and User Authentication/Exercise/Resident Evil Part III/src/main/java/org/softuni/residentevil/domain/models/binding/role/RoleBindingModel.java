package org.softuni.residentevil.domain.models.binding.role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.entities.Role;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class RoleBindingModel implements Bindable<Role>, Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long id;
}
