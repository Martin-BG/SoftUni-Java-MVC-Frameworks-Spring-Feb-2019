package org.softuni.cardealer.service;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.softuni.cardealer.repository.PartRepository;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;

    private final SupplierRepository supplierRepository;

    private final CarService carService;

    private final ModelMapper modelMapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, SupplierRepository supplierRepository, CarService carService) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PartServiceModel savePart(PartServiceModel partServiceModel) {
        Part part = this.modelMapper.map(partServiceModel, Part.class);
        part.setSupplier(this.supplierRepository.findByName(partServiceModel.getSupplier().getName()).orElse(null));
        this.partRepository.saveAndFlush(part);

        return this.modelMapper.map(part, PartServiceModel.class);
    }

    @Override
    public PartServiceModel editPart(String id, PartServiceModel partServiceModel) {
        Part part = this.partRepository.findById(id).orElse(null);
        part.setName(partServiceModel.getName());
        part.setPrice(partServiceModel.getPrice());

        Part edited = this.partRepository.saveAndFlush(part);

        return this.modelMapper.map(edited, PartServiceModel.class);

    }

    @Override
    public PartServiceModel deletePart(String id) {
        Part part = this.partRepository.findById(id).orElseThrow();

        this.carService.deletePartFromCars(part.getId());
        this.partRepository.delete(part);

        return this.modelMapper.map(part, PartServiceModel.class);
    }

    @Override
    public PartServiceModel findPartById(String id) {
        Part part = this.partRepository.findById(id).orElse(null);

        return this.modelMapper.map(part, PartServiceModel.class);
    }

    @Override
    public List<PartServiceModel> findAll() {
        return this.partRepository.findAll()
                .stream()
                .map(x -> this.modelMapper.map(x, PartServiceModel.class))
                .collect(Collectors.toList());
    }
}
