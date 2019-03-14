package org.softuni.cardealer.service;

import org.softuni.cardealer.domain.models.service.SupplierServiceModel;

public interface SupplierService {

    SupplierServiceModel saveSupplier(SupplierServiceModel supplierServiceModel);

    SupplierServiceModel editSupplier(SupplierServiceModel supplierServiceModel);

    SupplierServiceModel deleteSupplier(String id);

    SupplierServiceModel findSupplierById(String id);
}
