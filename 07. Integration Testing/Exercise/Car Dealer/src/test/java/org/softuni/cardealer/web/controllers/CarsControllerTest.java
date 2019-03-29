package org.softuni.cardealer.web.controllers;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.repository.CarRepository;
import org.softuni.cardealer.repository.PartRepository;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarsControllerTest {

    private static final String URL_CARS_BASE = "/cars";
    private static final String URL_CARS_ADD = URL_CARS_BASE + "/add";
    private static final String URL_CARS_EDIT = URL_CARS_BASE + "/edit";
    private static final String URL_CARS_EDIT_INVALID_ID = URL_CARS_EDIT + "/invalid-id";
    private static final String URL_CARS_DELETE = URL_CARS_BASE + "/delete";
    private static final String URL_CARS_DELETE_INVALID_ID = URL_CARS_DELETE + "/invalid-id";
    private static final String URL_CARS_ALL = URL_CARS_BASE + "/all";

    private static final String REDIRECT_URL_PATTERN_LOGIN = "**/users/login";

    private static final String VIEW_ALL_CARS = "all-cars";

    private static final String ATTRIBUTE_CARS = "cars";

    private static final String PARAM_MAKE = "make";
    private static final String PARAM_MODEL = "model";
    private static final String PARAM_TRAVEL_DISTANCE = "travelledDistance";
    private static final String PARAM_PARTS_LIST = "parts";

    private static final String CAR_MAKE = "Maker";
    private static final String CAR_MAKE_NEW = "New maker";
    private static final String CAR_MODEL = "Model";
    private static final String CAR_MODEL_NEW = "New Model";
    private static final String CAR_TRAVEL_DISTANCE = "100000";
    private static final String CAR_TRAVEL_DISTANCE_NEW = "99999";
    private static final String SUPPLIER_NAME = "Supplier";
    private static final String PART_NAME = "Part";

    private static MockHttpServletRequestBuilder postAddCarValidData;
    private static Supplier supplier;
    private static Part part;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PartRepository partRepository;

    @Before
    public void setUp() {
        carRepository.deleteAll();
        partRepository.deleteAll();
        supplierRepository.deleteAll();

        supplier = createSupplier(SUPPLIER_NAME);
        part = createPart(supplier, PART_NAME, BigDecimal.ONE);

        postAddCarValidData =
                post(URL_CARS_ADD)
                        .param(PARAM_MAKE, CAR_MAKE)
                        .param(PARAM_MODEL, CAR_MODEL)
                        .param(PARAM_TRAVEL_DISTANCE, CAR_TRAVEL_DISTANCE)
                        .param(PARAM_PARTS_LIST, part.getId());

    }

    private Car createCar() {
        Car car = new Car();
        car.setMake(CAR_MAKE);
        car.setModel(CAR_MODEL);
        car.setTravelledDistance(Long.parseLong(CAR_TRAVEL_DISTANCE));
        car.setParts(List.of(part));

        car = carRepository.save(car);
        return car;
    }

    private Supplier createSupplier(String name) {
        Supplier supplier = new Supplier();
        supplier.setName(name);
        return supplierRepository.save(supplier);
    }


    private Part createPart(Supplier supplier,
                            String partName,
                            BigDecimal price) {
        Part part = new Part();
        part.setName(partName);
        part.setPrice(price);
        part.setSupplier(supplier);

        part = partRepository.save(part);
        return part;
    }

    @Test
    @WithAnonymousUser
    public void addCar_post_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(postAddCarValidData)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test
    @WithMockUser
    public void addCar_post_validDataWithAuthenticatedUser_returnsCorrectViewAndStatus() throws Exception {
        // This test exposes a flaw in controller redirect logic:
        // use of relative from current url (/cars/add) redirect to "all",
        // instead of using absolute url ("/cars/all")

        mockMvc.perform(postAddCarValidData)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(URL_CARS_ALL));
    }

    @Test
    @WithMockUser
    public void addCar_post_validDataWithAuthenticatedUser_createsNewPart() throws Exception {
        mockMvc.perform(postAddCarValidData);

        List<Car> cars = carRepository.findAll();

        assertFalse("Car not created", cars.isEmpty());
        assertEquals("Incorrect car make", CAR_MAKE, cars.get(0).getMake());
        assertEquals("Incorrect car model", CAR_MODEL, cars.get(0).getModel());
        assertEquals("Incorrect travel distance", Long.parseLong(CAR_TRAVEL_DISTANCE), (long) (cars.get(0).getTravelledDistance()));
        assertEquals("Incorrect parts count", 1, cars.get(0).getParts().size());
        assertEquals("Incorrect part", part.getId(), cars.get(0).getParts().get(0).getId());
    }

    @Test(expected = Exception.class)
    @WithMockUser
    public void addCar_post_invalidPartIdWithAuthenticatedUser_throwsException() throws Exception {
        // Exposes issue with missing parts ID validation.
        // In result null value is added to parts:
        // org.softuni.cardealer.service.CarServiceImpl.java:35

        partRepository.deleteAll();

        mockMvc.perform(postAddCarValidData);
    }

    @Test
    @WithMockUser
    public void addCar_post_duplicatePartNameWithAuthenticatedUser_bothPartsCreatedSuccessfully() throws Exception {

        mockMvc.perform(postAddCarValidData);
        mockMvc.perform(postAddCarValidData);

        List<Car> cars = carRepository.findAll();

        assertEquals("Invalid number of cars created", 2, cars.size());
    }

    @Test
    @WithAnonymousUser
    public void editCar_post_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(post(URL_CARS_EDIT_INVALID_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test(expected = Exception.class)
    @WithMockUser
    public void editCar_post_invalidIdWithAuthenticatedUser_shouldThrowException() throws Exception {
        // Currently NestedServletException is thrown because of missing validation.
        // This is FAR from optimal behaviour.
        // org.softuni.cardealer.service.CarServiceImpl.editCar(CarServiceImpl.java:45)

        mockMvc.perform(post(URL_CARS_EDIT_INVALID_ID));
    }

    @Test
    @WithMockUser
    public void editCar_post_validDataWithAuthenticatedUser_returnsCorrectViewAndStatus() throws Exception {
        Car car = createCar();

        mockMvc.perform(post(URL_CARS_EDIT + "/" + car.getId())
                .param(PARAM_MAKE, CAR_MAKE_NEW)
                .param(PARAM_MODEL, CAR_MODEL_NEW)
                .param(PARAM_TRAVEL_DISTANCE, CAR_TRAVEL_DISTANCE_NEW)
                .param(PARAM_PARTS_LIST, part.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(URL_CARS_ALL));
    }

    @Test
    @WithMockUser
    public void editCar_post_validDataWithAuthenticatedUser_updateSucceeds() throws Exception {
        Car car = createCar();

        mockMvc.perform(post(URL_CARS_EDIT + "/" + car.getId())
                .param(PARAM_MAKE, CAR_MAKE_NEW)
                .param(PARAM_MODEL, CAR_MODEL_NEW)
                .param(PARAM_TRAVEL_DISTANCE, CAR_TRAVEL_DISTANCE_NEW))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(URL_CARS_ALL));

        Optional<Car> updated = carRepository.findById(car.getId());

        assertTrue("Updated Car not found in DB", updated.isPresent());
        assertEquals("Make not updated", CAR_MAKE_NEW, updated.get().getMake());
        assertEquals("Model not updated", CAR_MODEL_NEW, updated.get().getModel());
        assertEquals("Travelled distance not updated", Long.parseLong(CAR_TRAVEL_DISTANCE_NEW), (long) updated.get().getTravelledDistance());
        assertEquals("Parts size not correct", 1, updated.get().getParts().size());
    }

    @Test
    @WithAnonymousUser
    public void deleteCar_post_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(post(URL_CARS_DELETE_INVALID_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test(expected = Exception.class)
    @WithMockUser
    public void deleteCar_post_invalidIdWithAuthenticatedUser_throwsException() throws Exception {
        // Currently NestedServletException is thrown because of missing validation.
        // This is FAR from optimal behaviour.
        // org.softuni.cardealer.service.CarServiceImpl.java:57

        mockMvc.perform(post(URL_CARS_DELETE_INVALID_ID));
    }

    @Test
    @WithMockUser
    public void deleteCar_post_validIdWithAuthenticatedUser_deleteSucceeds() throws Exception {
        Car car = createCar();

        mockMvc.perform(post(URL_CARS_DELETE + "/" + car.getId()));

        Optional<Car> deleted = carRepository.findById(car.getId());

        assertFalse("Car not removed from DB", deleted.isPresent());
    }

    @Test
    @WithMockUser
    public void deleteCar_post_validIdWithAuthenticatedUser_returnsCorrectViewAndStatus() throws Exception {
        Car car = createCar();

        mockMvc.perform(post(URL_CARS_DELETE + "/" + car.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(URL_CARS_ALL));
    }

    @Test
    @WithAnonymousUser
    public void allCars_get_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(get(URL_CARS_ALL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test
    @WithMockUser
    public void allCars_get_withAuthenticatedUser_returnsCorrectViewAndStatus() throws Exception {
        createCar();

        mockMvc.perform(get(URL_CARS_ALL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_ALL_CARS))
                .andExpect(model().attributeExists(ATTRIBUTE_CARS))
                .andExpect(model().attribute(ATTRIBUTE_CARS, Matchers.hasSize(equalTo(1))));
    }
}
