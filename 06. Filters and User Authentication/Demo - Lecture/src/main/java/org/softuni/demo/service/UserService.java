package org.softuni.demo.service;

import org.softuni.demo.domain.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void register(User user);
}
