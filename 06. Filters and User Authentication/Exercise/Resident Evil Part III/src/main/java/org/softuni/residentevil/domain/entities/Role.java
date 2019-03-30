package org.softuni.residentevil.domain.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.softuni.residentevil.domain.converters.AuthorityConverter;
import org.softuni.residentevil.domain.enums.Authority;
import org.softuni.residentevil.domain.validation.annotations.composite.role.ValidRoleAuthority;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseLongEntity implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @ValidRoleAuthority
    @Convert(converter = AuthorityConverter.class)
    @Column(unique = true, nullable = false, length = ValidRoleAuthority.MAX_LENGTH)
    private Authority authority;

    @Override
    public String getAuthority() {
        return authority.role();
    }
}
