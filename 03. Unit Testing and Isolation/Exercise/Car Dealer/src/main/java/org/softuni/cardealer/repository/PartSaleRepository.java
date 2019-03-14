package org.softuni.cardealer.repository;

import org.softuni.cardealer.domain.entities.PartSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartSaleRepository extends JpaRepository<PartSale, String> {
}
