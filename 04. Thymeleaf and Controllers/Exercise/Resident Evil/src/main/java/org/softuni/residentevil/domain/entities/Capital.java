package org.softuni.residentevil.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "capitals")
public class Capital extends BaseLongEntity {

    @NotEmpty
    @Size(min = 1, max = 32)
    @Column(nullable = false, length = 32)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Double longitude;

    @NotNull
    @Column(nullable = false)
    private Double latitude;
}
