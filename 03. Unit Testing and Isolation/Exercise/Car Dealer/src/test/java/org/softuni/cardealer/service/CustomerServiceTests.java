package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CustomerServiceTests {

    @Mock
    private CustomerRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerServiceImpl service;


    @Before
    public void initTests() {
        Mockito.when(modelMapper.map(eq(null), any()))
                .thenThrow(new IllegalArgumentException());
    }

    @Test
    public void saveCustomer_validInputData_correctMethodsAndArgumentsUsed() {
        Customer customer = mock(Customer.class);
        CustomerServiceModel model = mock(CustomerServiceModel.class);
        Mockito.when(modelMapper.map(model, Customer.class)).thenReturn(customer);
        Mockito.when(repository.saveAndFlush(customer)).thenReturn(customer);
        Mockito.when(modelMapper.map(customer, CustomerServiceModel.class)).thenReturn(model);

        CustomerServiceModel result = service.saveCustomer(model);

        Mockito.verify(modelMapper).map(model, Customer.class);
        Mockito.verify(repository).saveAndFlush(customer);
        Mockito.verify(modelMapper).map(customer, CustomerServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomer_nullInput_throwsIllegalArgumentException() {
        service.saveCustomer(null);
    }

    @Test
    public void editCustomer_validInputData_correctMethodsAndArgumentsUsed() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        Customer customer = mock(Customer.class);
        CustomerServiceModel model = mock(CustomerServiceModel.class);
        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(model.getName()).thenReturn("name");
        Mockito.when(model.getBirthDate()).thenReturn(date);
        Mockito.when(model.isYoungDriver()).thenReturn(true);
        Mockito.when(repository.findById(eq("id"))).thenReturn(Optional.of(customer));
        Mockito.when(repository.saveAndFlush(customer)).thenReturn(customer);
        Mockito.when(modelMapper.map(customer, CustomerServiceModel.class)).thenReturn(model);

        CustomerServiceModel result = service.editCustomer(model);

        Mockito.verify(repository).findById("id");
        Mockito.verify(customer).setName("name");
        Mockito.verify(customer).setBirthDate(date);
        Mockito.verify(customer).setYoungDriver(true);
        Mockito.verify(repository).saveAndFlush(customer);
        Mockito.verify(modelMapper).map(customer, CustomerServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = NullPointerException.class)
    public void editCustomer_invalidId_throwsNullPointerException() {
        CustomerServiceModel model = mock(CustomerServiceModel.class);
        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(repository.findById("id")).thenReturn(Optional.empty());

        service.editCustomer(model);
    }

    @Test(expected = NullPointerException.class)
    public void editCustomer_nullInput_throwsNullPointerException() {
        service.editCustomer(null);
    }

    @Test
    public void deleteCustomer_validId_correctMethodsAndArgumentsUsed() {
        Customer customer = mock(Customer.class);
        CustomerServiceModel model = mock(CustomerServiceModel.class);
        Mockito.when(repository.findById("id")).thenReturn(Optional.of(customer));
        Mockito.when(modelMapper.map(customer, CustomerServiceModel.class)).thenReturn(model);

        CustomerServiceModel result = service.deleteCustomer("id");

        Mockito.verify(repository).findById("id");
        Mockito.verify(repository).delete(customer);
        Mockito.verify(modelMapper).map(customer, CustomerServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCustomer_nullInput_throwsIllegalArgumentException() {
        Mockito.when(repository.findById(null))
                .thenThrow(new IllegalArgumentException());

        service.deleteCustomer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCustomer_invalidId_throwsIllegalArgumentException() {
        Mockito.when(repository.findById("id"))
                .thenReturn(Optional.empty());
        doThrow(IllegalArgumentException.class)
                .when(repository)
                .delete(null);

        service.deleteCustomer("id");
    }

    @Test
    public void findCustomerById_validId_correctMethodsAndArgumentsUsed() {
        Customer customer = mock(Customer.class);
        CustomerServiceModel model = mock(CustomerServiceModel.class);
        Mockito.when(repository.findById("id")).thenReturn(Optional.of(customer));
        Mockito.when(modelMapper.map(customer, CustomerServiceModel.class)).thenReturn(model);

        CustomerServiceModel result = service.findCustomerById("id");

        Mockito.verify(repository).findById("id");
        Mockito.verify(modelMapper).map(customer, CustomerServiceModel.class);
        Assert.assertEquals(model, result);
    }


    @Test(expected = IllegalArgumentException.class)
    public void findCustomerById_nullInput_throwsIllegalArgumentException() {
        Mockito.when(repository.findById(null))
                .thenThrow(new IllegalArgumentException());

        service.findCustomerById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findCustomerById_invalidId_throwsIllegalArgumentException() {
        Mockito.when(repository.findById("id"))
                .thenReturn(Optional.empty());

        service.findCustomerById("id");
    }
}
