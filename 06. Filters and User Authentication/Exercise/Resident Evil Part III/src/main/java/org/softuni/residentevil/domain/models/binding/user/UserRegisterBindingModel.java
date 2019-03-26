package org.softuni.residentevil.domain.models.binding.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEmail;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserPassword;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserUsername;
import org.softuni.residentevil.domain.validation.annotations.custom.EqualFields;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = {"username"})
@NoArgsConstructor
@EqualFields(message = "{user.password.not-match}", fields = {"password", "confirmPassword"})
public class UserRegisterBindingModel implements Bindable<User>, Serializable {

    private static final long serialVersionUID = 1L;

    @ValidUserUsername
    private String username;

    @ValidUserPassword
    private String password;

    @ValidUserPassword
    private String confirmPassword;

    @ValidUserEmail
    private String email;
}
