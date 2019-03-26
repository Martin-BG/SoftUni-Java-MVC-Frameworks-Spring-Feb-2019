package org.softuni.residentevil.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.residentevil.domain.validation.annotations.composite.role.ValidRoleEntityAuthority;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseLongEntity implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;

    @ValidRoleEntityAuthority
    @Column(nullable = false, unique = true, length = ValidRoleEntityAuthority.MAX_LENGTH)
    private String authority;
}
