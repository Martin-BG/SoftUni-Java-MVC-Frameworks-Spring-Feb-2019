package org.softuni.cardealer.service;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerServiceModel saveCustomer(CustomerServiceModel customerServiceModel) {
        Customer customer = this.modelMapper.map(customerServiceModel, Customer.class);
        customer.setIsYoungDriver(LocalDate.now().getYear() - customer.getBirthDate().getYear() <= 21);
        customer = this.customerRepository.saveAndFlush(customer);

        return this.modelMapper.map(customer, CustomerServiceModel.class);

    }

    @Override
    public CustomerServiceModel editCustomer(String id, CustomerServiceModel customerServiceModel) {
        Customer customer = this.customerRepository.findById(id).orElse(null);

        customer.setName(customerServiceModel.getName());
        customer.setBirthDate(customerServiceModel.getBirthDate());
        customer.setIsYoungDriver(LocalDate.now().getYear() - customer.getBirthDate().getYear() <= 21);

        customer = this.customerRepository.saveAndFlush(customer);

        return this.modelMapper.map(customer, CustomerServiceModel.class);

    }

    @Override
    public CustomerServiceModel deleteCustomer(String id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        this.customerRepository.delete(customer);

        return this.modelMapper.map(customer, CustomerServiceModel.class);
    }

    @Override
    public CustomerServiceModel findCustomerById(String id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);

        return this.modelMapper.map(customer, CustomerServiceModel.class);
    }

    @Override
    public List<CustomerServiceModel> findAll() {
        return this.customerRepository.findAll()
                .stream()
                .map(x -> this.modelMapper.map(x, CustomerServiceModel.class))
                .collect(Collectors.toList());
    }
}
