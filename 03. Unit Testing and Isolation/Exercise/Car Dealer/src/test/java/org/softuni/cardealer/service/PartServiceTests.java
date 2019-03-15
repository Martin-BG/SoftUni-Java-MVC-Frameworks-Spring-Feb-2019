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
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PartServiceTests {

    @Mock
    private PartRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PartServiceImpl service;

    @Before
    public void initTests() {
        Mockito.when(modelMapper.map(eq(null), any()))
                .thenThrow(new IllegalArgumentException());
    }

    @Test
    public void savePart_validInputData_correctMethodsAndArgumentsUsed() {
        Part part = mock(Part.class);
        PartServiceModel model = mock(PartServiceModel.class);
        Mockito.when(modelMapper.map(model, Part.class)).thenReturn(part);
        Mockito.when(repository.saveAndFlush(part)).thenReturn(part);
        Mockito.when(modelMapper.map(part, PartServiceModel.class)).thenReturn(model);

        PartServiceModel result = service.savePart(model);

        Mockito.verify(modelMapper).map(model, Part.class);
        Mockito.verify(repository).saveAndFlush(part);
        Mockito.verify(modelMapper).map(part, PartServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void savePart_nullInput_throwsIllegalArgumentException() {
        service.savePart(null);
    }

    @Test
    public void editPart_validInputData_correctMethodsAndArgumentsUsed() {
        Part part = mock(Part.class);
        PartServiceModel model = mock(PartServiceModel.class);
        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(model.getName()).thenReturn("name");
        Mockito.when(model.getPrice()).thenReturn(BigDecimal.ONE);
        Mockito.when(repository.findById(eq("id"))).thenReturn(Optional.of(part));
        Mockito.when(repository.saveAndFlush(part)).thenReturn(part);
        Mockito.when(modelMapper.map(part, PartServiceModel.class)).thenReturn(model);

        PartServiceModel result = service.editPart(model);

        Mockito.verify(repository).findById("id");
        Mockito.verify(part).setName("name");
        Mockito.verify(part).setPrice(BigDecimal.ONE);
        Mockito.verify(repository).saveAndFlush(part);
        Mockito.verify(modelMapper).map(part, PartServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = NullPointerException.class)
    public void editPart_invalidId_throwsNullPointerException() {
        PartServiceModel model = mock(PartServiceModel.class);
        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(repository.findById("id")).thenReturn(Optional.empty());

        service.editPart(model);
    }

    @Test(expected = NullPointerException.class)
    public void editPart_nullInput_throwsNullPointerException() {
        service.editPart(null);
    }

    @Test
    public void deletePart_validId_correctMethodsAndArgumentsUsed() {
        Part part = mock(Part.class);
        PartServiceModel model = mock(PartServiceModel.class);
        Mockito.when(repository.findById("id")).thenReturn(Optional.of(part));
        Mockito.when(modelMapper.map(part, PartServiceModel.class)).thenReturn(model);

        PartServiceModel result = service.deletePart("id");

        Mockito.verify(repository).findById("id");
        Mockito.verify(repository).delete(part);
        Mockito.verify(modelMapper).map(part, PartServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deletePart_nullInput_throwsIllegalArgumentException() {
        Mockito.when(repository.findById(null))
                .thenThrow(new IllegalArgumentException());

        service.deletePart(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void deletePart_invalidId_throwsNoSuchElementException() {
        Mockito.when(repository.findById("id"))
                .thenReturn(Optional.empty());

        service.deletePart("id");
    }

    @Test
    public void findPartById_validId_correctMethodsAndArgumentsUsed() {
        Part part = mock(Part.class);
        PartServiceModel model = mock(PartServiceModel.class);
        Mockito.when(repository.findById("id")).thenReturn(Optional.of(part));
        Mockito.when(modelMapper.map(part, PartServiceModel.class)).thenReturn(model);

        PartServiceModel result = service.findPartById("id");

        Mockito.verify(repository).findById("id");
        Mockito.verify(modelMapper).map(part, PartServiceModel.class);
        Assert.assertEquals(model, result);
    }


    @Test(expected = IllegalArgumentException.class)
    public void findPartById_nullInput_throwsIllegalArgumentException() {
        Mockito.when(repository.findById(null))
                .thenThrow(new IllegalArgumentException());

        service.findPartById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findPartById_invalidId_throwsIllegalArgumentException() {
        Mockito.when(repository.findById("id"))
                .thenReturn(Optional.empty());

        service.findPartById("id");
    }
}
