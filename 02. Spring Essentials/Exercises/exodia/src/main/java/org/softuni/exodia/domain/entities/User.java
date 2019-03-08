package org.softuni.exodia.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseUuidEntity {

    @NotBlank
    @Size(min = 1, max = 32)
    @Column(unique = true, nullable = false, length = 32)
    private String username;

    @Getter
    @NotNull
    @Size(min = 75, max = 75)
    @Column(nullable = false, length = 75)
    private String password;

    @NotNull
    @Email
    @Size(min = 1, max = 64)
    @Column(unique = true, nullable = false, length = 64)
    private String email;
}
