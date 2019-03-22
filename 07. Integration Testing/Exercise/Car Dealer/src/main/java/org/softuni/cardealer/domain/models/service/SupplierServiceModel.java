package org.softuni.cardealer.domain.models.service;

public class SupplierServiceModel extends BaseServiceModel {

    private String name;

    private boolean isImporter;

    public SupplierServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsImporter() {
        return isImporter;
    }

    public void setIsImporter(boolean importer) {
        isImporter = importer;
    }
}
