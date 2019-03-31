package org.softuni.residentevil.domain.models.view.user;

import lombok.*;
import org.softuni.residentevil.domain.api.Viewable;
import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.enums.Authority;

import java.io.Serializable;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public final class UserViewModel implements Viewable<User>, Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;

    private String username;

    private String email;

    @Setter
    private Authority authority;
}
