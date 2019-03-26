package org.softuni.residentevil.domain.models.binding.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.models.binding.role.RoleBindingModel;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEmail;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEncryptedPassword;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEntityAuthorities;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserUsername;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = {"username"})
@NoArgsConstructor
public class UserBindingModel implements Bindable<User>, Serializable {

    private static final long serialVersionUID = 1L;

    @ValidUserUsername
    private String username;

    @ValidUserEncryptedPassword
    private String password;

    @ValidUserEmail
    private String email;

    @ValidUserEntityAuthorities
    private Set<@Valid RoleBindingModel> authorities = new HashSet<>();

    private boolean isAccountNonLocked = true;
    private boolean isAccountNonExpired = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    public UserBindingModel(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
