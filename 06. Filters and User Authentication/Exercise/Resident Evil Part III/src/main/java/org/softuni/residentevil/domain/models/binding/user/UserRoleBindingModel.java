package org.softuni.residentevil.domain.models.binding.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.validation.annotations.composite.role.ValidRoleAuthority;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class UserRoleBindingModel implements Bindable<User>, Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private UUID id;

    @ValidRoleAuthority
    private String role;
}
