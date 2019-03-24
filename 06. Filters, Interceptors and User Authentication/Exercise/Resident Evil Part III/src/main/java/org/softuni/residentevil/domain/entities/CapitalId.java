package org.softuni.residentevil.domain.entities;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Targets the same table as {@link Capital} entity, but contains ID only.
 * <p>
 * For use by {@link Virus} as optimization: only required data
 * (id in this case) is retrieved from the database.
 */

@NoArgsConstructor
@Entity
@Table(name = "capitals")
public class CapitalId extends BaseLongEntity implements Serializable {

    private static final long serialVersionUID = 1L;
}
