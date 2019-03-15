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
import org.softuni.cardealer.domain.entities.CarSale;
import org.softuni.cardealer.domain.entities.PartSale;
import org.softuni.cardealer.domain.models.service.CarSaleServiceModel;
import org.softuni.cardealer.domain.models.service.PartSaleServiceModel;
import org.softuni.cardealer.repository.CarSaleRepository;
import org.softuni.cardealer.repository.PartSaleRepository;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SaleServiceTests {


    @Mock
    private CarSaleRepository carSaleRepository;

    @Mock
    private PartSaleRepository partSaleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SaleServiceImpl service;

    @Before
    public void initTests() {
        Mockito.when(modelMapper.map(eq(null), any()))
                .thenThrow(new IllegalArgumentException());
    }

    @Test
    public void saleCar_validInputData_correctMethodsAndArgumentsUsed() {
        CarSale carSale = mock(CarSale.class);
        CarSaleServiceModel model = mock(CarSaleServiceModel.class);
        Mockito.when(modelMapper.map(model, CarSale.class)).thenReturn(carSale);
        Mockito.when(carSaleRepository.saveAndFlush(carSale)).thenReturn(carSale);
        Mockito.when(modelMapper.map(carSale, CarSaleServiceModel.class)).thenReturn(model);

        CarSaleServiceModel result = service.saleCar(model);

        Mockito.verify(modelMapper).map(model, CarSale.class);
        Mockito.verify(carSaleRepository).saveAndFlush(carSale);
        Mockito.verify(modelMapper).map(carSale, CarSaleServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saleCar_nullInput_throwsIllegalArgumentException() {
        service.saleCar(null);
    }

    @Test
    public void salePart_validInputData_correctMethodsAndArgumentsUsed() {
        PartSale partSale = mock(PartSale.class);
        PartSaleServiceModel model = mock(PartSaleServiceModel.class);
        Mockito.when(modelMapper.map(model, PartSale.class)).thenReturn(partSale);
        Mockito.when(partSaleRepository.saveAndFlush(partSale)).thenReturn(partSale);
        Mockito.when(modelMapper.map(partSale, PartSaleServiceModel.class)).thenReturn(model);

        PartSaleServiceModel result = service.salePart(model);

        Mockito.verify(modelMapper).map(model, PartSale.class);
        Mockito.verify(partSaleRepository).saveAndFlush(partSale);
        Mockito.verify(modelMapper).map(partSale, PartSaleServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void salePart_nullInput_throwsIllegalArgumentException() {
        service.saleCar(null);
    }
}
