package org.softuni.residentevil.domain.entities;

import lombok.NoArgsConstructor;
import org.softuni.residentevil.annotations.composite.capital.ValidCapitalLatitude;
import org.softuni.residentevil.annotations.composite.capital.ValidCapitalLongitude;
import org.softuni.residentevil.annotations.composite.capital.ValidCapitalName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "capitals")
public class Capital extends BaseLongEntity {

    private static final long serialVersionUID = 1L;

    @ValidCapitalName
    @Column(nullable = false, length = ValidCapitalName.MAX_LENGTH)
    private String name;

    @ValidCapitalLongitude
    @Column(nullable = false)
    private Double longitude;

    @ValidCapitalLatitude
    @Column(nullable = false)
    private Double latitude;
}
