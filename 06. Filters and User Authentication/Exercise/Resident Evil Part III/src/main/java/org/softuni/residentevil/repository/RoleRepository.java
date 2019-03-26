package org.softuni.residentevil.repository;

import org.softuni.residentevil.domain.entities.Role;
import org.softuni.residentevil.domain.validation.annotations.composite.role.ValidRoleEntityAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
@Repository
public interface RoleRepository extends GenericRepository<Role, Long> {

    Optional<Role> findRoleByAuthority(@ValidRoleEntityAuthority String authority);
}
