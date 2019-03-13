package org.softuni.exodia.repository;

import org.softuni.exodia.annotations.validation.composite.ValidUserEmail;
import org.softuni.exodia.annotations.validation.composite.ValidUserUsername;
import org.softuni.exodia.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Validated
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUsername(@ValidUserUsername String username);

    long countAllByUsernameEquals(@ValidUserUsername String username);

    long countAllByEmailEquals(@ValidUserEmail String email);
}
