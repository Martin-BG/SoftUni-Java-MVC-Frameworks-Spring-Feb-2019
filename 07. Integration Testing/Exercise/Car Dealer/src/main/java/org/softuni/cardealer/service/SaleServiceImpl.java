package org.softuni.cardealer.service;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.CarSale;
import org.softuni.cardealer.domain.entities.PartSale;
import org.softuni.cardealer.domain.models.service.CarSaleServiceModel;
import org.softuni.cardealer.domain.models.service.PartSaleServiceModel;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.CarSaleRepository;
import org.softuni.cardealer.repository.PartSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {

    private final CarSaleRepository carSaleRepository;
    private final PartSaleRepository partSaleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImpl(CarSaleRepository carSaleRepository, PartSaleRepository partSaleRepository, ModelMapper modelMapper) {
        this.carSaleRepository = carSaleRepository;
        this.partSaleRepository = partSaleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CarSaleServiceModel saleCar(CarSaleServiceModel carSaleServiceModel) {
        CarSale carSale = this.modelMapper.map(carSaleServiceModel, CarSale.class);
        carSale = this.carSaleRepository.saveAndFlush(carSale);

        return this.modelMapper.map(carSale, CarSaleServiceModel.class);
    }

    @Override
    public PartSaleServiceModel salePart(PartSaleServiceModel partSaleServiceModel) {
        PartSale partSale = this.modelMapper.map(partSaleServiceModel, PartSale.class);
        partSale = this.partSaleRepository.saveAndFlush(partSale);

        return this.modelMapper.map(partSale, PartSaleServiceModel.class);
    }
}
