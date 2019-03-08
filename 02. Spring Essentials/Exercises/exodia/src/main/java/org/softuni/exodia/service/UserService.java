package org.softuni.exodia.service;

import org.softuni.exodia.domain.api.Viewable;
import org.softuni.exodia.domain.entities.User;
import org.softuni.exodia.domain.models.binding.user.UserLoginBindingModel;
import org.softuni.exodia.domain.models.binding.user.UserRegisterBindingModel;
import org.softuni.exodia.domain.models.view.user.UserLoggedViewModel;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends Service<User, UUID> {

    boolean register(UserRegisterBindingModel bindingModel);

    Optional<UserLoggedViewModel> login(UserLoginBindingModel bindingModel);

    <V extends Viewable<User>> Optional<V> findByUsername(String username, Class<V> viewModelClass);
}
