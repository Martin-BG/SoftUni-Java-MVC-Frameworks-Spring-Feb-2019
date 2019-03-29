package org.softuni.cardealer.web.controllers;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SuppliersControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private static final String URL_SUPPLIERS_BASE = "/suppliers";
    private static final String URL_SUPPLIERS_ADD = URL_SUPPLIERS_BASE + "/add";
    private static final String URL_SUPPLIERS_EDIT = URL_SUPPLIERS_BASE + "/edit";
    private static final String URL_SUPPLIERS_EDIT_INVALID_ID = URL_SUPPLIERS_EDIT + "/invalid-id";
    private static final String URL_SUPPLIERS_DELETE = URL_SUPPLIERS_BASE + "/delete";
    private static final String URL_SUPPLIERS_DELETE_INVALID_ID = URL_SUPPLIERS_DELETE + "/invalid-id";
    private static final String URL_SUPPLIERS_ALL = URL_SUPPLIERS_BASE + "/all";
    private static final String URL_SUPPLIERS_FETCH = URL_SUPPLIERS_BASE + "/fetch";

    private static final String REDIRECT_URL_PATTERN_LOGIN = "**/users/login";

    private static final String PARAM_NAME = "name";
    private static final String PARAM_IS_IMPORTER = "isImporter";

    private static final String SUPPLIER_IMPORTER = "on";
    private static final String SUPPLIER_NAME = "supplier";
    private static final String SUPPLIER_NAME_NEW = "New supplier";

    private static final MockHttpServletRequestBuilder POST_ADD_SUPPLIER_VALID_DATA_IMPORTER =
            post(URL_SUPPLIERS_ADD)
                    .param(PARAM_NAME, SUPPLIER_NAME)
                    .param(PARAM_IS_IMPORTER, SUPPLIER_IMPORTER);

    private static final MockHttpServletRequestBuilder POST_ADD_SUPPLIER_VALID_DATA_NOT_IMPORTER =
            post(URL_SUPPLIERS_ADD)
                    .param(PARAM_NAME, SUPPLIER_NAME);
    private static final String VIEW_ALL_SUPPLIERS = "all-suppliers";
    private static final String ATTRIBUTE_SUPPLIERS = "suppliers";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SupplierRepository supplierRepository;

    @Before
    public void setUp() {
        supplierRepository.deleteAll();
    }

    private Supplier createSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName(SUPPLIER_NAME);
        return supplierRepository.save(supplier);
    }

    @Test
    @WithAnonymousUser
    public void addSupplier_post_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(POST_ADD_SUPPLIER_VALID_DATA_IMPORTER)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test
    @WithMockUser
    public void addSupplier_post_validDataWithAuthenticatedUser_returnsCorrectViewAndStatus() throws Exception {
        // This test exposes a flaw in controller redirect logic:
        // use of relative from current url (/suppliers/add) redirect to "all",
        // instead of using absolute url ("/suppliers/all")

        mockMvc.perform(POST_ADD_SUPPLIER_VALID_DATA_IMPORTER)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(URL_SUPPLIERS_ALL));
    }

    @Test
    @WithMockUser
    public void addSupplier_post_validDataImporterWithAuthenticatedUser_createsNewImporterSupplier() throws Exception {
        mockMvc.perform(POST_ADD_SUPPLIER_VALID_DATA_IMPORTER);

        Optional<Supplier> supplier = supplierRepository.findByName(SUPPLIER_NAME);

        assertTrue("Supplier not created", supplier.isPresent());
        assertTrue("Supplier is not set as Importer", supplier.get().getIsImporter());
    }

    @Test
    @WithMockUser
    public void addSupplier_post_validDataNotImporterWithAuthenticatedUser_createsNewNotImporterSupplier() throws Exception {
        mockMvc.perform(POST_ADD_SUPPLIER_VALID_DATA_NOT_IMPORTER);

        Optional<Supplier> supplier = supplierRepository.findByName(SUPPLIER_NAME);

        assertTrue("Supplier not created", supplier.isPresent());
        assertFalse("Supplier is improperly set as Importer", supplier.get().getIsImporter());
    }

    @Test
    @WithMockUser
    public void addSupplier_post_duplicateSupplierNameWithAuthenticatedUser_bothSuppliersCreatedSuccessfully() throws Exception {
        mockMvc.perform(POST_ADD_SUPPLIER_VALID_DATA_IMPORTER);
        mockMvc.perform(POST_ADD_SUPPLIER_VALID_DATA_NOT_IMPORTER);

        List<Supplier> suppliers = supplierRepository.findAll()
                .stream()
                .filter(supplier -> SUPPLIER_NAME.equals(supplier.getName()))
                .collect(Collectors.toList());

        assertEquals("Invalid count of suppliers created", 2, suppliers.size());
    }

    @Test
    @WithAnonymousUser
    public void editSupplier_post_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(post(URL_SUPPLIERS_EDIT_INVALID_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test(expected = Exception.class)
    @WithMockUser
    public void editSupplier_post_invalidIdWithAuthenticatedUser_shouldThrowException() throws Exception {
        // Currently NullPointerException is thrown because of missing validation.
        // This is FAR from optimal behaviour.
        // org.softuni.cardealer.service.SupplierServiceImpl.editSupplier(SupplierServiceImpl.java:37)

        mockMvc.perform(post(URL_SUPPLIERS_EDIT_INVALID_ID));
    }


    @Test
    @WithMockUser
    public void editSupplier_post_validDataChangeNameWithAuthenticatedUser_returnsCorrectViewAndStatus() throws Exception {
        Supplier supplier = createSupplier();

        mockMvc.perform(post(URL_SUPPLIERS_EDIT + "/" + supplier.getId())
                .param(PARAM_NAME, SUPPLIER_NAME_NEW))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(URL_SUPPLIERS_ALL));
    }

    @Test
    @WithMockUser
    public void editSupplier_post_validDataWithAuthenticatedUser_updateSucceeds() throws Exception {
        Supplier supplier = createSupplier();

        mockMvc.perform(post(URL_SUPPLIERS_EDIT + "/" + supplier.getId())
                .param(PARAM_NAME, SUPPLIER_NAME_NEW)
                .param(PARAM_IS_IMPORTER, SUPPLIER_IMPORTER));

        Optional<Supplier> updated = supplierRepository.findById(supplier.getId());

        assertTrue("Updated Supplier not found in DB", updated.isPresent());
        assertEquals("Supplier name not updated", SUPPLIER_NAME_NEW, updated.get().getName());
        assertTrue("Supplier Importer status not updated", updated.get().getIsImporter());
    }

    @Test
    @WithAnonymousUser
    public void deleteSupplier_post_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(post(URL_SUPPLIERS_DELETE_INVALID_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test(expected = Exception.class)
    @WithMockUser
    public void deleteSupplier_post_invalidIdWithAuthenticatedUser_throwsException() throws Exception {
        // Currently NestedServletException is thrown because of missing validation.
        // This is FAR from optimal behaviour.
        // org.softuni.cardealer.service.SupplierServiceImpl.deleteSupplier(SupplierServiceImpl.java:48)

        mockMvc.perform(post(URL_SUPPLIERS_DELETE_INVALID_ID));
    }

    @Test
    @WithMockUser
    public void deleteSupplier_post_validIdWithAuthenticatedUser_deleteSucceeds() throws Exception {
        Supplier supplier = createSupplier();

        mockMvc.perform(post(URL_SUPPLIERS_DELETE + "/" + supplier.getId()));

        Optional<Supplier> updated = supplierRepository.findById(supplier.getId());

        assertFalse("Supplier not removed from DB", updated.isPresent());
    }

    @Test
    @WithMockUser
    public void deleteSupplier_post_validIdWithAuthenticatedUser_returnsCorrectViewAndStatus() throws Exception {
        Supplier supplier = createSupplier();

        mockMvc.perform(post(URL_SUPPLIERS_DELETE + "/" + supplier.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(URL_SUPPLIERS_ALL));
    }

    @Test
    @WithAnonymousUser
    public void allSuppliers_get_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(get(URL_SUPPLIERS_ALL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test
    @WithMockUser
    public void allSuppliers_get_withAuthenticatedUser_returnsCorrectViewAndStatus() throws Exception {
        createSupplier();

        mockMvc.perform(get(URL_SUPPLIERS_ALL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_ALL_SUPPLIERS))
                .andExpect(model().attributeExists(ATTRIBUTE_SUPPLIERS))
                .andExpect(model().attribute(ATTRIBUTE_SUPPLIERS, Matchers.hasSize(equalTo(1))));
    }

    @Test
    @WithAnonymousUser
    public void fetchSuppliers_get_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(get(URL_SUPPLIERS_FETCH))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test
    @WithMockUser
    public void fetchSuppliers_get_withAuthenticatedUser_returnsCorrectTypeAndStatus() throws Exception {
        mockMvc.perform(get(URL_SUPPLIERS_FETCH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    @WithMockUser
    public void fetchSuppliers_get_withAuthenticatedUser_returnsCorrectData() throws Exception {
        Supplier supplierOne = new Supplier();
        supplierOne.setName(SUPPLIER_NAME);
        supplierRepository.save(supplierOne);

        Supplier supplierTwo = new Supplier();
        supplierTwo.setName(SUPPLIER_NAME_NEW);
        supplierTwo.setIsImporter(true);
        supplierRepository.save(supplierTwo);

        List<Supplier> suppliers = supplierRepository.findAll();

        mockMvc.perform(get(URL_SUPPLIERS_FETCH))
                .andExpect(jsonPath("$", hasSize(suppliers.size())))
                .andExpect(jsonPath("$[0].id", is(suppliers.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(suppliers.get(0).getName())))
                .andExpect(jsonPath("$[0].isImporter", is(suppliers.get(0).getIsImporter())))
                .andExpect(jsonPath("$[1].id", is(suppliers.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(suppliers.get(1).getName())))
                .andExpect(jsonPath("$[1].isImporter", is(suppliers.get(1).getIsImporter())));
    }
}
