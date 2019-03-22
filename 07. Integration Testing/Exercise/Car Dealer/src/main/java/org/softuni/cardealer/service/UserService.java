package org.softuni.cardealer.service;

import org.softuni.cardealer.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserServiceModel saveUser(UserServiceModel userServiceModel);

    List<UserServiceModel> findAllUsers();

    UserServiceModel findUserByUsername(String username);
}
