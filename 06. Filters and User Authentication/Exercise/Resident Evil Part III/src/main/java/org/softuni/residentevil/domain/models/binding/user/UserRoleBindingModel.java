package org.softuni.residentevil.domain.models.binding.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.validation.annotations.composite.role.ValidRoleAuthority;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserUsername;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = {"username"})
@NoArgsConstructor
public class UserRoleBindingModel implements Bindable<User>, Serializable {

    private static final long serialVersionUID = 1L;

    @ValidUserUsername
    private String username;

    @ValidRoleAuthority
    private String role;
}
