package org.softuni.cardealer.domain.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class Sale extends BaseEntity {

    private Double discount;
    private Customer customer;

    public Sale() {
    }

    @Column(name = "discount", nullable = false)
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
