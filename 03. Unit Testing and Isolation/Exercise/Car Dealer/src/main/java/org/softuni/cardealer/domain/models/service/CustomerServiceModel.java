package org.softuni.cardealer.domain.models.service;

import java.time.LocalDate;

public class CustomerServiceModel extends BaseServiceModel {

    private String name;
    private LocalDate birthDate;
    private boolean isYoungDriver;

    public CustomerServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
