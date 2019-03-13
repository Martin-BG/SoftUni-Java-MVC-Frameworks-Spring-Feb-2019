package org.softuni.exodia.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.exodia.annotations.validation.composite.ValidUserEmail;
import org.softuni.exodia.annotations.validation.composite.ValidUserHashedPassword;
import org.softuni.exodia.annotations.validation.composite.ValidUserUsername;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseUuidEntity {

    @ValidUserUsername
    @Column(unique = true, nullable = false, length = 32)
    private String username;

    @Getter
    @ValidUserHashedPassword
    @Column(nullable = false, length = 75)
    private String password;

    @ValidUserEmail
    @Column(unique = true, nullable = false, length = 64)
    private String email;
}
