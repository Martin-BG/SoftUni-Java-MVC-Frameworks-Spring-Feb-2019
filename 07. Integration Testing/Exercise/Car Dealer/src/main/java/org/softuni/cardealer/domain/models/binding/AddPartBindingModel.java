package org.softuni.cardealer.domain.models.binding;

import java.math.BigDecimal;

public class AddPartBindingModel {
    private String name;

    private BigDecimal price;

    private String supplier;

    public AddPartBindingModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
