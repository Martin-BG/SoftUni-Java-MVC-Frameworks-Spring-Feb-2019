package org.softuni.residentevil.service;

import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.api.Viewable;
import org.softuni.residentevil.domain.entities.Role;
import org.softuni.residentevil.domain.enums.Authority;
import org.softuni.residentevil.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Log
@Service
@Validated
@Transactional
public class RoleServiceImpl extends BaseService<Role, Long, RoleRepository> implements RoleService {

    private static final String ROLES = "rolesCache";
    private static final String ROLES_FOR_AUTHORITY = "rolesForAuthorityCache";

    @Autowired
    public RoleServiceImpl(RoleRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Logger logger() {
        return log;
    }

    @PostConstruct
    @Transactional
    @CacheEvict(cacheNames = {ROLES, ROLES_FOR_AUTHORITY}, allEntries = true)
    public void initRoles() {
        if (repository.count() == 0L) {
            Set<Role> roles = Arrays.stream(Authority.values())
                    .map(Role::new)
                    .collect(Collectors.toSet());
            repository.saveAll(roles);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ROLES, key = "#authority")
    public <V extends Viewable<Role>> Optional<V>
    findByAuthority(@NotNull Authority authority, @NotNull Class<V> viewModelClass) {
        return repository
                .findRoleByAuthority(authority)
                .map(role -> mapper.map(role, viewModelClass));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ROLES_FOR_AUTHORITY, key = "#authority")
    public List<Role> getRolesForAuthority(@NotNull Authority authority) {
        List<Role> roles = repository.findAll();

        roles.removeIf(role -> role.getAuthority().equals(Authority.ROOT.role()));

        switch (authority) {
        case MODERATOR:
            roles.removeIf(role -> role.getAuthority().equals(Authority.ADMIN.role()));
            break;
        case USER:
            roles.removeIf(role -> !role.getAuthority().equals(Authority.USER.role()));
            break;
        default:
            break;
        }

        return roles;
    }
}
