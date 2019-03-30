package org.softuni.residentevil.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.residentevil.domain.validation.annotations.composite.role.ValidRoleAuthority;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseLongEntity implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @ValidRoleAuthority
    @Column(unique = true, nullable = false, length = ValidRoleAuthority.MAX_LENGTH)
    private String authority;
}
