package org.softuni.cardealer.domain.models.binding;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AddCustomerBindingModel {
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    public AddCustomerBindingModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
