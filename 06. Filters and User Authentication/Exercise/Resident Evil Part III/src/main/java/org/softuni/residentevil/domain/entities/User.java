package org.softuni.residentevil.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserAuthorities;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEmail;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEncryptedPassword;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserUsername;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findUserEager",
        query = "SELECT u FROM User u LEFT JOIN FETCH u.authorities AS a WHERE u.username = :username")
public class User extends BaseUuidEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @ValidUserUsername
    @Column(unique = true, nullable = false, updatable = false, length = ValidUserUsername.MAX_LENGTH)
    private String username;

    @ValidUserEncryptedPassword
    @Column(nullable = false, length = ValidUserEncryptedPassword.MAX_LENGTH)
    private String password;

    @ValidUserEmail
    @Column(unique = true, nullable = false, length = ValidUserEmail.MAX_LENGTH)
    private String email;

    @ValidUserAuthorities
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> authorities = new HashSet<>();

    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
}
