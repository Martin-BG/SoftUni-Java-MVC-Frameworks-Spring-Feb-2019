package org.softuni.realestate.repository;

import org.softuni.realestate.domain.enities.Offer;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public interface OfferRepository extends CrudRepository<Offer, UUID> {

    List<Offer> findAllByApartmentTypeEquals(@NotBlank @Size(min = 1, max = 255) String apartmentType);
}
