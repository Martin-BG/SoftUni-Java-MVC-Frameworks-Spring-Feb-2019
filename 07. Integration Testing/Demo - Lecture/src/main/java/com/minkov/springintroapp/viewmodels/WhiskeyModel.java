package com.minkov.springintroapp.viewmodels;

import com.minkov.springintroapp.validators.CapitalCase;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WhiskeyModel {
    @NotNull(message = "Not null")
    @Size(min = 3, max = 15, message = "Invalid length")
    @CapitalCase
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
