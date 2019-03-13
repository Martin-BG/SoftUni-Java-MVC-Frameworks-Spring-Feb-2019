package org.softuni.exodia.domain.models.binding.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.exodia.annotations.validation.composite.ValidUserEmail;
import org.softuni.exodia.annotations.validation.composite.ValidUserHashedPassword;
import org.softuni.exodia.annotations.validation.composite.ValidUserUsername;
import org.softuni.exodia.domain.api.Bindable;
import org.softuni.exodia.domain.entities.User;

@Getter
@Setter
@NoArgsConstructor
public class UserHashedPasswordBindingModel implements Bindable<User> {

    @ValidUserUsername
    private String username;

    @ValidUserHashedPassword
    private String password;

    @ValidUserEmail
    private String email;
}
