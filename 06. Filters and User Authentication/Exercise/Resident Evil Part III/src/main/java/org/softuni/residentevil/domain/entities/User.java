package org.softuni.residentevil.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEmail;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEntityPassword;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserUsername;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    @ValidUserEntityPassword
    @Column(nullable = false)
    private String password;

    @ValidUserEmail
    @Column(unique = true, nullable = false, length = ValidUserEmail.MAX_LENGTH)
    private String email;

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> authorities = new HashSet<>();

    private boolean isAccountNonLocked = true;
    private boolean isAccountNonExpired = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
}
