package org.softuni.cardealer.domain.models.service;

public class PartSaleServiceModel extends SaleServiceModel {

    private Integer quantity;
    private PartServiceModel part;

    public PartSaleServiceModel() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public PartServiceModel getPart() {
        return part;
    }

    public void setPart(PartServiceModel part) {
        this.part = part;
    }
}
