package org.softuni.residentevil.service;

import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.models.binding.user.UserRegisterBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface UserService extends Service<User, UUID>, UserDetailsService {

    void registerUser(@NotNull @Valid UserRegisterBindingModel bindingModel);
}
