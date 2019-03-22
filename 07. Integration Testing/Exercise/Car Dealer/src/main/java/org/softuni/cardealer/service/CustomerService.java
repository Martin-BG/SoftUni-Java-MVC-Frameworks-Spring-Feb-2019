package org.softuni.cardealer.service;

import org.softuni.cardealer.domain.models.service.CustomerServiceModel;

import java.util.List;

public interface CustomerService {

    CustomerServiceModel saveCustomer(CustomerServiceModel customerServiceModel);

    CustomerServiceModel editCustomer(String id, CustomerServiceModel customerServiceModel);

    CustomerServiceModel deleteCustomer(String id);

    CustomerServiceModel findCustomerById(String id);

    List<CustomerServiceModel> findAll();
}
