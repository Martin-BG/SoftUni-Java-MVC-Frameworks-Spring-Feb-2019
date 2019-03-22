package org.softuni.cardealer.domain.models.binding;

import org.softuni.cardealer.domain.entities.Part;

import java.util.ArrayList;
import java.util.List;

public class AddCarBindingModel {
    private String make;

    private String model;

    private Long travelledDistance;

    private List<String> parts;

    public AddCarBindingModel() {
        this.parts = new ArrayList<>();
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return this.travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<String> getParts() {
        return this.parts;
    }

    public void setParts(List<String> parts) {
        this.parts = parts;
    }
}
