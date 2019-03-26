package org.softuni.residentevil.service;

import org.softuni.residentevil.domain.api.Viewable;
import org.softuni.residentevil.domain.entities.Role;
import org.softuni.residentevil.domain.enums.Authority;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface RoleService extends Service<Role, Long> {

    <V extends Viewable<Role>> Optional<V>
    findByAuthority(@NotNull Authority authority, @NotNull Class<V> viewModelClass);
}
