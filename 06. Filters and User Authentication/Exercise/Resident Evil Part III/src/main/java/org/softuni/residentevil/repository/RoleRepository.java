package org.softuni.residentevil.repository;

import org.softuni.residentevil.domain.entities.Role;
import org.softuni.residentevil.domain.validation.annotations.composite.role.ValidRoleEntityAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Validated
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findRoleByAuthority(@ValidRoleEntityAuthority String authority);
}
