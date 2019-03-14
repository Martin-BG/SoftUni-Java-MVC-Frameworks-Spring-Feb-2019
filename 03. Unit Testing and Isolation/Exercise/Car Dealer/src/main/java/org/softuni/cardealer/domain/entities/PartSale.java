package org.softuni.cardealer.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "part_sales")
public class PartSale extends Sale {

    private Integer quantity;
    private Part part;

    public PartSale() {
    }

    @Column(name = "quantity", nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(targetEntity = Part.class)
    @JoinColumn(name = "part_id", referencedColumnName = "id")
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
