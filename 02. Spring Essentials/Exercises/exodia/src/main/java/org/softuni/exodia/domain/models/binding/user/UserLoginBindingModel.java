package org.softuni.exodia.domain.models.binding.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.exodia.annotations.validation.composite.ValidUserPassword;
import org.softuni.exodia.annotations.validation.composite.ValidUserUsername;
import org.softuni.exodia.domain.api.Bindable;
import org.softuni.exodia.domain.entities.User;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginBindingModel implements Bindable<User> {

    @ValidUserUsername
    private String username;

    @ValidUserPassword
    private String password;
}
