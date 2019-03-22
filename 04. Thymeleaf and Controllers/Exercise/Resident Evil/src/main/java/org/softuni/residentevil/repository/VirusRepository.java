package org.softuni.residentevil.repository;

import org.softuni.residentevil.domain.entities.Virus;
import org.softuni.residentevil.domain.models.view.virus.VirusSimpleViewModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
@Repository
public interface VirusRepository extends GenericRepository<Virus, UUID> {

    @Query(value = "SELECT " +
            "NEW org.softuni.residentevil.domain.models.view.virus.VirusSimpleViewModel(" +
            "v.id, v.name, v.magnitude, v.releasedOn) " +
            "FROM Virus AS v " +
            "ORDER BY v.releasedOn ASC")
    List<VirusSimpleViewModel> findAllSimpleView();
}
