package org.softuni.cardealer.repository;

import org.softuni.cardealer.domain.entities.CarSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarSaleRepository extends JpaRepository<CarSale, String> {
}
