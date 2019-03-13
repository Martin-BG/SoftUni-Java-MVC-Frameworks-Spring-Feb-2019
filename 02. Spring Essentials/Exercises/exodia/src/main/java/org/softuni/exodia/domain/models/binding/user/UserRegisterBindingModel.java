package org.softuni.exodia.domain.models.binding.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.exodia.annotations.validation.EqualFields;
import org.softuni.exodia.annotations.validation.composite.ValidUserEmail;
import org.softuni.exodia.annotations.validation.composite.ValidUserPassword;
import org.softuni.exodia.annotations.validation.composite.ValidUserUsername;
import org.softuni.exodia.domain.api.Bindable;
import org.softuni.exodia.domain.entities.User;

@Getter
@Setter
@NoArgsConstructor
@EqualFields(message = "Passwords do not match", value = {"password", "confirmPassword"})
public class UserRegisterBindingModel implements Bindable<User> {

    @ValidUserUsername
    private String username;

    @ValidUserPassword
    private String password;

    @ValidUserPassword
    private String confirmPassword;

    @ValidUserEmail
    private String email;
}
