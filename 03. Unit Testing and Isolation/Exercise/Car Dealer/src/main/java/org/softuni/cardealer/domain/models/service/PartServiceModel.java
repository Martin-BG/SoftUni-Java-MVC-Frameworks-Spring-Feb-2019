package org.softuni.cardealer.domain.models.service;

import java.math.BigDecimal;

public class PartServiceModel extends BaseServiceModel {

    private String name;
    private BigDecimal price;
    private SupplierServiceModel supplier;

    public PartServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SupplierServiceModel getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierServiceModel supplier) {
        this.supplier = supplier;
    }
}
