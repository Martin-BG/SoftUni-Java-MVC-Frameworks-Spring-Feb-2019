package org.softuni.residentevil.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEmail;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEntityPassword;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserUsername;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findUserEager",
        query = "SELECT u FROM User u LEFT JOIN FETCH u.authorities AS a WHERE u.username = :username")
public class User extends BaseUuidEntity implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;
    boolean isAccountNonLocked = true;
    boolean isAccountNonExpired = true;
    boolean isCredentialsNonExpired = true;
    boolean isEnabled = true;
    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    Set<Role> authorities = new HashSet<>();
    @ValidUserUsername
    @Column(nullable = false, unique = true, updatable = false, length = ValidUserUsername.MAX_LENGTH)
    private String username;
    @ValidUserEntityPassword
    @Column(nullable = false)
    private String password;
    @ValidUserEmail
    @Column(nullable = false, unique = true, length = ValidUserEmail.MAX_LENGTH)
    private String email;
}
