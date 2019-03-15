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
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SupplierServiceTests {


    @Mock
    private SupplierRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SupplierServiceImpl service;

    @Before
    public void initTests() {
        Mockito.when(modelMapper.map(eq(null), any()))
                .thenThrow(new IllegalArgumentException());
    }

    @Test
    public void saveSupplier_validInputData_correctMethodsAndArgumentsUsed() {
        Supplier supplier = mock(Supplier.class);
        SupplierServiceModel model = mock(SupplierServiceModel.class);
        Mockito.when(modelMapper.map(model, Supplier.class)).thenReturn(supplier);
        Mockito.when(repository.saveAndFlush(supplier)).thenReturn(supplier);
        Mockito.when(modelMapper.map(supplier, SupplierServiceModel.class)).thenReturn(model);

        SupplierServiceModel result = service.saveSupplier(model);

        Mockito.verify(modelMapper).map(model, Supplier.class);
        Mockito.verify(repository).saveAndFlush(supplier);
        Mockito.verify(modelMapper).map(supplier, SupplierServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveSupplier_nullInput_throwsIllegalArgumentException() {
        service.saveSupplier(null);
    }

    @Test
    public void editSupplier_validInputData_correctMethodsAndArgumentsUsed() {
        Supplier supplier = mock(Supplier.class);
        SupplierServiceModel model = mock(SupplierServiceModel.class);
        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(model.getName()).thenReturn("name");
        Mockito.when(model.isImporter()).thenReturn(true);
        Mockito.when(repository.findById(eq("id"))).thenReturn(Optional.of(supplier));
        Mockito.when(repository.saveAndFlush(supplier)).thenReturn(supplier);
        Mockito.when(modelMapper.map(supplier, SupplierServiceModel.class)).thenReturn(model);

        SupplierServiceModel result = service.editSupplier(model);

        Mockito.verify(repository).findById("id");
        Mockito.verify(supplier).setName("name");
        Mockito.verify(supplier).setImporter(true);
        Mockito.verify(repository).saveAndFlush(supplier);
        Mockito.verify(modelMapper).map(supplier, SupplierServiceModel.class);
        Assert.assertEquals(model, result);
    }


    @Test(expected = NullPointerException.class)
    public void editSupplier_invalidId_throwsNullPointerException() {
        SupplierServiceModel model = mock(SupplierServiceModel.class);
        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(repository.findById("id")).thenReturn(Optional.empty());

        service.editSupplier(model);
    }

    @Test(expected = NullPointerException.class)
    public void editSupplier_nullInput_throwsNullPointerException() {
        service.editSupplier(null);
    }


    @Test
    public void deleteSupplier_validId_correctMethodsAndArgumentsUsed() {
        Supplier supplier = mock(Supplier.class);
        SupplierServiceModel model = mock(SupplierServiceModel.class);
        Mockito.when(repository.findById("id")).thenReturn(Optional.of(supplier));
        Mockito.when(modelMapper.map(supplier, SupplierServiceModel.class)).thenReturn(model);

        SupplierServiceModel result = service.deleteSupplier("id");

        Mockito.verify(repository).findById("id");
        Mockito.verify(repository).delete(supplier);
        Mockito.verify(modelMapper).map(supplier, SupplierServiceModel.class);
        Assert.assertEquals(model, result);
    }


    @Test(expected = IllegalArgumentException.class)
    public void deleteSupplier_nullInput_throwsIllegalArgumentException() {
        Mockito.when(repository.findById(null))
                .thenThrow(new IllegalArgumentException());

        service.deleteSupplier(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteSupplier_invalidId_throwsIllegalArgumentException() {
        Mockito.when(repository.findById("id"))
                .thenReturn(Optional.empty());
        doThrow(IllegalArgumentException.class)
                .when(repository)
                .delete(null);

        service.deleteSupplier("id");
    }

    @Test
    public void findSupplierById_validId_correctMethodsAndArgumentsUsed() {
        Supplier supplier = mock(Supplier.class);
        SupplierServiceModel model = mock(SupplierServiceModel.class);
        Mockito.when(repository.findById("id")).thenReturn(Optional.of(supplier));
        Mockito.when(modelMapper.map(supplier, SupplierServiceModel.class)).thenReturn(model);

        SupplierServiceModel result = service.findSupplierById("id");

        Mockito.verify(repository).findById("id");
        Mockito.verify(modelMapper).map(supplier, SupplierServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findSupplierById_nullInput_throwsIllegalArgumentException() {
        Mockito.when(repository.findById(null))
                .thenThrow(new IllegalArgumentException());

        service.findSupplierById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findSupplierById_invalidId_throwsIllegalArgumentException() {
        Mockito.when(repository.findById("id"))
                .thenReturn(Optional.empty());

        service.findSupplierById("id");
    }
}