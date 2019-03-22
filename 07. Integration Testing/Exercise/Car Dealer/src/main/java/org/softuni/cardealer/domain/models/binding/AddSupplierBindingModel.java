package org.softuni.cardealer.domain.models.binding;

public class AddSupplierBindingModel {
    private String name;

    private boolean isImporter;

    public AddSupplierBindingModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsImporter() {
        return this.isImporter;
    }

    public void setIsImporter(boolean importer) {
        isImporter = importer;
    }
}
