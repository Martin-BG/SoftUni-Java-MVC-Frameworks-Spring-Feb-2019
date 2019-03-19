package org.softuni.residentevil.domain.entities;

import lombok.NoArgsConstructor;
import org.softuni.residentevil.domain.converters.CreatorConverter;
import org.softuni.residentevil.domain.converters.MagnitudeConverter;
import org.softuni.residentevil.domain.enums.Creator;
import org.softuni.residentevil.domain.enums.Magnitude;
import org.softuni.residentevil.domain.enums.Mutation;
import org.softuni.residentevil.domain.validation.annotations.composite.virus.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "viruses")
public class Virus extends BaseUuidEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ValidVirusName
    @Column(unique = true, nullable = false, length = ValidVirusName.MAX_LENGTH)
    private String name;

    @ValidVirusDescription
    @Column(nullable = false, columnDefinition = "TEXT", length = ValidVirusDescription.MAX_LENGTH)
    private String description;

    @ValidVirusSideEffects
    @Column(length = ValidVirusSideEffects.MAX_LENGTH)
    private String sideEffects;

    @ValidVirusCreator
    @Convert(converter = CreatorConverter.class)
    @Column(nullable = false, length = ValidVirusCreator.MAX_LENGTH)
    private Creator creator;

    @ValidVirusIsDeadly
    @Column(nullable = false)
    private Boolean isDeadly;

    @ValidVirusIsCurable
    @Column(nullable = false)
    private Boolean isCurable;

    @ValidVirusMutation
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = ValidVirusMutation.MAX_LENGTH)
    private Mutation mutation;

    @ValidVirusTurnoverRate
    @Column(nullable = false)
    private Integer turnoverRate;

    @ValidVirusHoursUntilMutation
    @Column(nullable = false)
    private Integer hoursUntilMutation;

    @ValidVirusMagnitude
    @Convert(converter = MagnitudeConverter.class)
    @Column(nullable = false, length = ValidVirusMagnitude.MAX_LENGTH)
    private Magnitude magnitude;

    @ValidVirusReleasedOn
    @Column(nullable = false)
    private LocalDate releasedOn;

    /**
     * For ManyToMany relations recommended collection is Set instead of List
     * because with List on each add or remove all elements are removed and then added back.
     *
     * @see <a href="https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/">
     * The best way to use the @ManyToMany annotation with JPA and Hibernate</a> from Vlad Mihalcea<br>
     * @see <a href="https://bianjp.com/posts/2017/10/31/jpa-many-to-many-update-efficiency">JPA many-to-many update efficiency</a>
     */
    @ValidVirusCapitals
    @ManyToMany
    @JoinTable(
            name = "viruses_capitals",
            joinColumns = {@JoinColumn(name = "virus_id")},
            inverseJoinColumns = {@JoinColumn(name = "capital_id")})
    private Set<Capital> capitals = new HashSet<>();
}
