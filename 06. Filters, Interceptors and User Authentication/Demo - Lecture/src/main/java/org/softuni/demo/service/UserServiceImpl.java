package org.softuni.demo.service;

import org.softuni.demo.domain.entities.User;
import org.softuni.demo.domain.entities.UserRole;
import org.softuni.demo.repository.UserRepository;
import org.softuni.demo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails result = this.userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return result;
    }

    @Override
    public void register(User user) {
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(getRolesForRegistration());
        this.userRepository.save(user);
    }

    private Set<UserRole> getRolesForRegistration() {
        Set<UserRole> roles = new HashSet<UserRole>();

        if (this.userRepository.count() == 0) {
            roles.add(this.userRoleRepository.findByName("ADMIN"));
        } else {
            roles.add(this.userRoleRepository.findByName("USER"));
        }

        return roles;
    }
}
