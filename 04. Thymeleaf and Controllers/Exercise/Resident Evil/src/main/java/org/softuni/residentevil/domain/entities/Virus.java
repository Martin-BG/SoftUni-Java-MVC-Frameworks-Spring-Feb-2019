package org.softuni.residentevil.domain.entities;

import lombok.NoArgsConstructor;
import org.softuni.residentevil.annotations.composite.virus.*;
import org.softuni.residentevil.domain.converters.CreatorConverter;
import org.softuni.residentevil.domain.converters.MagnitudeConverter;
import org.softuni.residentevil.domain.enums.Creator;
import org.softuni.residentevil.domain.enums.Magnitude;
import org.softuni.residentevil.domain.enums.Mutation;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "viruses")
public class Virus extends BaseUuidEntity {

    @ValidVirusName
    @Column(unique = true, nullable = false, length = 10)
    private String name;

    @ValidVirusDescription
    @Column(nullable = false, columnDefinition = "TEXT", length = 100)
    private String description;

    @ValidVirusSideEffects
    @Column(length = 50)
    private String sideEffects;

    @ValidVirusCreator
    @Convert(converter = CreatorConverter.class)
    @Column(nullable = false, length = 4)
    private Creator creator;

    @ValidVirusIsDeadly
    @Column(nullable = false)
    private Boolean isDeadly;

    @ValidVirusIsCurable
    @Column(nullable = false)
    private Boolean isCurable;

    @ValidVirusMutation
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private Mutation mutation;

    @ValidVirusTurnoverRate
    @Column(nullable = false)
    private Integer turnoverRate;

    @ValidVirusHoursUntilMutation
    @Column(nullable = false)
    private Integer hoursUntilMutation;

    @ValidVirusMagnitude
    @Convert(converter = MagnitudeConverter.class)
    @Column(nullable = false, length = 6)
    private Magnitude magnitude;

    @ValidVirusReleasedOn
    @Column(nullable = false)
    private LocalDate releasedOn;

    @ManyToMany
    @JoinTable(
            name = "viruses_capitals",
            joinColumns = {@JoinColumn(name = "virus_id")},
            inverseJoinColumns = {@JoinColumn(name = "capital_id")})
    private List<Capital> capitals = new ArrayList<>();
}
