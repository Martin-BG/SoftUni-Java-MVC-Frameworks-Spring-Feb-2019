package org.softuni.residentevil.service;

import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.models.binding.user.UserRegisterBindingModel;
import org.softuni.residentevil.domain.models.binding.user.UserRoleBindingModel;
import org.softuni.residentevil.domain.models.view.user.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface UserService extends Service<User, UUID>, UserDetailsService {

    void registerUser(@NotNull @Valid UserRegisterBindingModel bindingModel);

    List<UserViewModel> allUsers();

    void updateRole(@NotNull @Valid UserRoleBindingModel userRoleBindingModel);
}
