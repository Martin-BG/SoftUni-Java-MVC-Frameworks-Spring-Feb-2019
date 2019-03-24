package com.minkov.springintroapp.dtos;

public class CarDto {
    private long id;
    private String model;

    public long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
