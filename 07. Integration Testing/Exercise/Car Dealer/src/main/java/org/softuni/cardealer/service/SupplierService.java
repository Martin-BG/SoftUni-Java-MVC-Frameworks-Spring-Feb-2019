package org.softuni.cardealer.service;

import org.softuni.cardealer.domain.models.service.SupplierServiceModel;

import java.util.List;

public interface SupplierService {

    SupplierServiceModel saveSupplier(SupplierServiceModel supplierServiceModel);

    SupplierServiceModel editSupplier(String id, SupplierServiceModel supplierServiceModel);

    SupplierServiceModel deleteSupplier(String id);

    SupplierServiceModel findSupplierById(String id);

    List<SupplierServiceModel> findAll();
}
