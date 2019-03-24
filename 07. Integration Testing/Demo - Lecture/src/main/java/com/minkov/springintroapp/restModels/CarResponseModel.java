package com.minkov.springintroapp.restModels;

public class CarResponseModel {
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
