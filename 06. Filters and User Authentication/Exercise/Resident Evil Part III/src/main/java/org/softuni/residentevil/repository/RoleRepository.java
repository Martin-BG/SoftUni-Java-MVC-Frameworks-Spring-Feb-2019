package org.softuni.residentevil.repository;

import org.softuni.residentevil.domain.entities.Role;
import org.softuni.residentevil.domain.enums.Authority;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
@Repository
public interface RoleRepository extends GenericRepository<Role, Long> {

    Optional<Role> findRoleByAuthority(@NotNull Authority authority);
}
