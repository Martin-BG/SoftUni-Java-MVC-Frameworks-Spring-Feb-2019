package org.softuni.cardealer.service;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SupplierServiceModel saveSupplier(SupplierServiceModel supplierServiceModel) {
        Supplier supplier = this.modelMapper.map(supplierServiceModel, Supplier.class);
        this.supplierRepository.saveAndFlush(supplier);

        return this.modelMapper.map(supplier, SupplierServiceModel.class);

    }

    @Override
    public SupplierServiceModel editSupplier(String id, SupplierServiceModel supplierServiceModel) {
        Supplier supplier = this.supplierRepository.findById(id).orElse(null);
        supplier.setName(supplierServiceModel.getName());
        supplier.setIsImporter(supplierServiceModel.getIsImporter());
        Supplier result = this.supplierRepository.saveAndFlush(supplier);

        return this.modelMapper.map(result, SupplierServiceModel.class);

    }

    @Override
    public SupplierServiceModel deleteSupplier(String id) {
        Supplier supplier = this.supplierRepository.findById(id).orElse(null);
        this.supplierRepository.delete(supplier);

        return this.modelMapper.map(supplier, SupplierServiceModel.class);

    }

    @Override
    public SupplierServiceModel findSupplierById(String id) {
        Supplier supplier = this.supplierRepository.findById(id).orElse(null);

        return this.modelMapper.map(supplier, SupplierServiceModel.class);

    }

    @Override
    public List<SupplierServiceModel> findAll() {
        return this.supplierRepository.findAll()
                .stream()
                .map(x -> this.modelMapper.map(x, SupplierServiceModel.class))
                .collect(Collectors.toList());
    }
}
