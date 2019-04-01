package org.softuni.residentevil.service;

import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.entities.Role;
import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.enums.Authority;
import org.softuni.residentevil.domain.models.binding.role.RoleBindingModel;
import org.softuni.residentevil.domain.models.binding.user.UserBindingModel;
import org.softuni.residentevil.domain.models.binding.user.UserRegisterBindingModel;
import org.softuni.residentevil.domain.models.binding.user.UserRoleBindingModel;
import org.softuni.residentevil.domain.models.view.user.UserViewModel;
import org.softuni.residentevil.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Log
@Service("userDetailsService")
@Validated
@Transactional
public class UserServiceImpl extends BaseService<User, UUID, UserRepository> implements UserService {

    private static final String USERS = "usersCache";
    private static final String ALL_USERS = "allUsersCache";

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
    @Cacheable(cacheNames = USERS, key = "#username")
    public UserDetails loadUserByUsername(String username) {
        return repository
                .findUserEager(username)
                .orElseThrow(() -> USERNAME_NOT_FOUND_EXCEPTION);
    }

    private static boolean isNotRoot(User user) {
        return user
                .getAuthorities()
                .stream()
                .map(Role::getAuthority)
                .noneMatch(role -> Authority.ROOT.role().equals(role));
    }

    private static Authority getHighestAuthority(Set<String> roles) {
        Authority authority;

        if (roles.contains(Authority.ROOT.role())) {
            authority = Authority.ROOT;
        } else if (roles.contains(Authority.ADMIN.role())) {
            authority = Authority.ADMIN;
        } else if (roles.contains(Authority.MODERATOR.role())) {
            authority = Authority.MODERATOR;
        } else {
            authority = Authority.USER;
        }

        return authority;
    }

    @Override
    @CacheEvict(cacheNames = ALL_USERS, allEntries = true)
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

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ALL_USERS, sync = true)
    public List<UserViewModel> allUsers() {
        return repository
                .findAll()
                .stream()
                .map(this::mapUserToViewModel)
                .collect(Collectors.toList());
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

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = ALL_USERS, allEntries = true),
            @CacheEvict(cacheNames = USERS, key = "#userRoleBindingModel.username")})
    public void updateRole(@NotNull @Valid UserRoleBindingModel userRoleBindingModel) {
        Authority authority = Authority
                .fromRole(userRoleBindingModel.getRole());

        if (authority == null) {
            throw new IllegalArgumentException(
                    "Invalid role: " + userRoleBindingModel.getRole());
        }

        User user = repository
                .findUserEager(userRoleBindingModel.getUsername())
                .filter(UserServiceImpl::isNotRoot)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found or not allowed for edit: " + userRoleBindingModel.getUsername()));

        List<Role> rolesForAuthority = roleService
                .getRolesForAuthority(authority);

        user.getAuthorities()
                .retainAll(rolesForAuthority);

        user.getAuthorities()
                .addAll(rolesForAuthority);
    }

    private UserViewModel mapUserToViewModel(User user) {
        UserViewModel viewModel = mapper
                .map(user, UserViewModel.class);

        Set<String> roles = user
                .getAuthorities()
                .stream()
                .map(Role::getAuthority)
                .collect(Collectors.toSet());

        Authority authority = getHighestAuthority(roles);

        viewModel.setAuthority(authority);

        return viewModel;
    }
}
