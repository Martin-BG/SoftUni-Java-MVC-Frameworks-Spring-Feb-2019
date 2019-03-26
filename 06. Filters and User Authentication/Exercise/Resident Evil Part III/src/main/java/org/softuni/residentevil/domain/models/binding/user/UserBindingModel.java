package org.softuni.residentevil.domain.models.binding.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.models.binding.role.RoleBindingModel;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEmail;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEntityAuthorities;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserEntityPassword;
import org.softuni.residentevil.domain.validation.annotations.composite.user.ValidUserUsername;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = {"username"})
@NoArgsConstructor
public class UserBindingModel implements Bindable<User>, Serializable {

    private static final long serialVersionUID = 1L;

    @ValidUserUsername
    private String username;

    @ValidUserEntityPassword
    private String password;

    @ValidUserEmail
    private String email;

    @ValidUserEntityAuthorities
    private Set<@Valid RoleBindingModel> authorities;

    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
}
