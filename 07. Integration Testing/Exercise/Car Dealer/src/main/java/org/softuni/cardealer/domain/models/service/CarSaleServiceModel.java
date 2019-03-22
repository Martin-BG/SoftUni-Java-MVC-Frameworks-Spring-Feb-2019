package org.softuni.cardealer.domain.models.service;

public class CarSaleServiceModel extends SaleServiceModel {

    private CarServiceModel car;

    public CarSaleServiceModel() {
    }

    public CarServiceModel getCar() {
        return car;
    }

    public void setCar(CarServiceModel car) {
        this.car = car;
    }
}
