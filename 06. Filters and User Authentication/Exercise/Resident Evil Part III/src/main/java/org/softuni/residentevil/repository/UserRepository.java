package org.softuni.residentevil.repository;

import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEmail;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserUsername;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Validated
@Repository
public interface UserRepository extends GenericRepository<User, UUID> {

    Optional<User> findUserEager(@ValidUserUsername String username);

    long countByUsername(@ValidUserUsername String username);

    long countByEmail(@ValidUserEmail String email);
}
