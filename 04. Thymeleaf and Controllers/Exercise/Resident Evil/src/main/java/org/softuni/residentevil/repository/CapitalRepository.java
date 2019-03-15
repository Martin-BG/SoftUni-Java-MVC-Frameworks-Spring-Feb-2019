package org.softuni.residentevil.repository;

import org.softuni.residentevil.domain.entities.Capital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Validated
@Repository
public interface CapitalRepository extends JpaRepository<Capital, Long> {

}
