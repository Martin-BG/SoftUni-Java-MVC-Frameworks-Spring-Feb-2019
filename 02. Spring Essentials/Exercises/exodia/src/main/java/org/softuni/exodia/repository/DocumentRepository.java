package org.softuni.exodia.repository;

import org.softuni.exodia.domain.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Tuple;
import java.util.List;
import java.util.UUID;

@Validated
@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {

    @Query("SELECT d.id as id, d.title as title FROM Document AS d")
    List<Tuple> findAllShortView();
}
