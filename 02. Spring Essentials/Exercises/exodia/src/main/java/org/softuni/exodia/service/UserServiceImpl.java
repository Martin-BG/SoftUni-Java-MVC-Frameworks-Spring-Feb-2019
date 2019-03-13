package org.softuni.exodia.service;

import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.softuni.exodia.domain.api.Viewable;
import org.softuni.exodia.domain.entities.User;
import org.softuni.exodia.domain.models.binding.user.UserHashedPasswordBindingModel;
import org.softuni.exodia.domain.models.binding.user.UserLoginBindingModel;
import org.softuni.exodia.domain.models.binding.user.UserRegisterBindingModel;
import org.softuni.exodia.domain.models.view.user.UserLoggedViewModel;
import org.softuni.exodia.repository.UserRepository;
import org.softuni.exodia.util.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log
@Service
public class UserServiceImpl extends BaseService<User, UUID, UserRepository> implements UserService {

    private final PasswordHasher passwordHasher;

    @Autowired
    public UserServiceImpl(UserRepository repository,
                           Validator validator,
                           ModelMapper mapper,
                           PasswordHasher passwordHasher) {
        super(repository, validator, mapper);
        this.passwordHasher = passwordHasher;
    }

    @Override
    protected Logger logger() {
        return log;
    }

    @Override
    public boolean register(UserRegisterBindingModel bindingModel) {
        if (!validator.validate(bindingModel).isEmpty()) {
            log.log(Level.WARNING, "[User Registration failed] Constraint violations detected");
            return false;
        }

        if (repository.countAllByUsernameEquals(bindingModel.getUsername()) > 0) {
            log.log(Level.WARNING, "[User Registration failed] Username already used: " + bindingModel.getUsername());
            return false;
        }

        if (repository.countAllByEmailEquals(bindingModel.getEmail()) > 0) {
            log.log(Level.WARNING, "[User Registration failed] Email already used: " + bindingModel.getEmail());
            return false;
        }

        UserHashedPasswordBindingModel user = mapper.map(bindingModel, UserHashedPasswordBindingModel.class);
        String encodedPassword = passwordHasher.encodedHash(bindingModel.getPassword().toCharArray());
        user.setPassword(encodedPassword);

        return create(user);
    }

    @Override
    public Optional<UserLoggedViewModel> login(UserLoginBindingModel bindingModel) {
        if (bindingModel == null || !validator.validate(bindingModel).isEmpty()) {
            return Optional.empty();
        }
        return repository
                .findUserByUsername(bindingModel.getUsername())
                .filter(u -> passwordHasher.verifyEncoded(u.getPassword(), bindingModel.getPassword().toCharArray()))
                .map(u -> mapper.map(u, UserLoggedViewModel.class));
    }

    @Override
    public <V extends Viewable<User>> Optional<V> findByUsername(String username, Class<V> viewModelClass) {
        return repository
                .findUserByUsername(username)
                .map(user -> mapper.map(user, viewModelClass));
    }
}
