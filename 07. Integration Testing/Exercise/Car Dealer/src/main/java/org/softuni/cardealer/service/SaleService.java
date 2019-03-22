package org.softuni.cardealer.service;

import org.softuni.cardealer.domain.models.service.CarSaleServiceModel;
import org.softuni.cardealer.domain.models.service.PartSaleServiceModel;

public interface SaleService {

    CarSaleServiceModel saleCar(CarSaleServiceModel carSaleServiceModel);

    PartSaleServiceModel salePart(PartSaleServiceModel partSaleServiceModel);
}
