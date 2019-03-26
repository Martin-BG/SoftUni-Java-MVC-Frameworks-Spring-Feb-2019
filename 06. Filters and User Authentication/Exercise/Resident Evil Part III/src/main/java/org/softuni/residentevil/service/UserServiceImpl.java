package org.softuni.residentevil.service;

import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.enums.Authority;
import org.softuni.residentevil.domain.models.binding.role.RoleBindingModel;
import org.softuni.residentevil.domain.models.binding.user.UserBindingModel;
import org.softuni.residentevil.domain.models.binding.user.UserRegisterBindingModel;
import org.softuni.residentevil.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Log
@Service
@Validated
@Transactional
public class UserServiceImpl extends BaseService<User, UUID, UserRepository> implements UserService {

    private static final UsernameNotFoundException USERNAME_NOT_FOUND_EXCEPTION =
            new UsernameNotFoundException("Username not found");

    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository,
                           ModelMapper mapper,
                           RoleService roleService,
                           BCryptPasswordEncoder passwordEncoder) {
        super(repository, mapper);
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected Logger logger() {
        return log;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        return repository
                .findUserEager(username)
                .orElseThrow(() -> USERNAME_NOT_FOUND_EXCEPTION);
    }

    @Override
    public void registerUser(@NotNull @Valid UserRegisterBindingModel bindingModel) {
        if (repository.countByEmail(bindingModel.getEmail()) > 0) {
            throw new IllegalArgumentException("Email already used: " + bindingModel.getEmail());
        }

        if (repository.countByUsername(bindingModel.getUsername()) > 0) {
            throw new IllegalArgumentException("Username already used: " + bindingModel.getUsername());
        }

        UserBindingModel user = new UserBindingModel(
                bindingModel.getUsername(),
                bindingModel.getEmail(),
                passwordEncoder.encode(bindingModel.getPassword()));
        user.getAuthorities().addAll(getRolesForUser());

        create(user);
    }


    private List<RoleBindingModel> getRolesForUser() {
        if (repository.count() == 0L) {
            return roleService.findAll(RoleBindingModel.class);
        } else {
            return List.of(roleService
                    .findByAuthority(Authority.USER, RoleBindingModel.class)
                    .orElseThrow());
        }
    }
}
