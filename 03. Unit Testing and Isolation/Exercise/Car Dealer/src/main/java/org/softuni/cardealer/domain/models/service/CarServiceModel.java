package org.softuni.cardealer.domain.models.service;

import java.util.List;

public class CarServiceModel extends BaseServiceModel {

    private String make;
    private String model;
    private Long travelledDistance;
    private List<PartServiceModel> parts;

    public CarServiceModel() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<PartServiceModel> getParts() {
        return parts;
    }

    public void setParts(List<PartServiceModel> parts) {
        this.parts = parts;
    }
}
