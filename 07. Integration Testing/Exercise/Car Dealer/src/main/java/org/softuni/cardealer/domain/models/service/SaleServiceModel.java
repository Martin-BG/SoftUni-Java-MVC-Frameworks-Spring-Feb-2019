package org.softuni.cardealer.domain.models.service;

public abstract class SaleServiceModel extends BaseServiceModel {

    private Double discount;
    private CustomerServiceModel customer;

    public SaleServiceModel() {
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public CustomerServiceModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerServiceModel customer) {
        this.customer = customer;
    }
}
