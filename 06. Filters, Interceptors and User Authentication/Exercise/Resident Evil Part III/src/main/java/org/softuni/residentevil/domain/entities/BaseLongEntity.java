package org.softuni.residentevil.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter(AccessLevel.PRIVATE)
@Getter
@MappedSuperclass
abstract class BaseLongEntity extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;
}
