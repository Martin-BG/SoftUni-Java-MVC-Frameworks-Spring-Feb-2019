package org.softuni.residentevil.service;

import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserUsername;
import org.softuni.residentevil.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;
import java.util.logging.Logger;

@Log
@Service
@Validated
@Transactional
public class UserService extends BaseService<User, UUID, UserRepository> implements UserDetailsService {

    private static final UsernameNotFoundException USERNAME_NOT_FOUND_EXCEPTION = new UsernameNotFoundException("Username not found");

    public UserService(UserRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Logger logger() {
        return log;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(@ValidUserUsername String username) throws UsernameNotFoundException {
        return repository
                .findUserEager(username)
                .orElseThrow(() -> USERNAME_NOT_FOUND_EXCEPTION);
    }
}
