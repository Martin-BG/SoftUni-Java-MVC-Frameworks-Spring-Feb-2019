package org.softuni.cardealer.web.controllers;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.repository.CustomerRepository;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomersControllerTest {

    private static final String URL_CUSTOMERS_BASE = "/customers";
    private static final String URL_CUSTOMERS_ADD = URL_CUSTOMERS_BASE + "/add";
    private static final String URL_CUSTOMERS_ALL = URL_CUSTOMERS_BASE + "/all";

    private static final String REDIRECT_URL_PATTERN_LOGIN = "**/users/login";

    private static final String VIEW_ALL_CUSTOMERS = "all-customers";

    private static final String ATTRIBUTE_CUSTOMERS = "customers";

    private static final String PARAM_NAME = "name";
    private static final String PARAM_BIRTH_DATE = "birthDate";

    private static final String CUSTOMER_NAME = "Customer";
    private static final String CUSTOMER_BIRTH_DATE = "2000-10-31";

    private static final MockHttpServletRequestBuilder POST_ADD_CUSTOMER_VALID_DATA = post(URL_CUSTOMERS_ADD)
            .param(PARAM_NAME, CUSTOMER_NAME)
            .param(PARAM_BIRTH_DATE, CUSTOMER_BIRTH_DATE);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    @WithAnonymousUser
    public void addCustomer_post_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(POST_ADD_CUSTOMER_VALID_DATA)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test
    @WithMockUser
    public void addCustomer_post_validDataWithAuthenticatedUser_returnsCorrectViewAndStatus() throws Exception {
        // This test exposes a flaw in controller redirect logic:
        // use of relative from current url (/customers/add) redirect to "all",
        // instead of using absolute url ("/customers/all")

        mockMvc.perform(POST_ADD_CUSTOMER_VALID_DATA)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(URL_CUSTOMERS_ALL));
    }

    @Test
    @WithMockUser
    public void addCustomer_post_validDataWithAuthenticatedUser_createsNewCustomer() throws Exception {
        mockMvc.perform(POST_ADD_CUSTOMER_VALID_DATA);

        List<Customer> customers = customerRepository.findAll();

        assertFalse("Customer not created", customers.isEmpty());
        assertEquals("Incorrect customer name", CUSTOMER_NAME, customers.get(0).getName());
        assertEquals("Incorrect birth date",
                LocalDate.parse(CUSTOMER_BIRTH_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                customers.get(0).getBirthDate());
    }

    @Test
    @WithMockUser
    public void addCustomer_post_duplicateNameWithAuthenticatedUser_bothPartsCreatedSuccessfully() throws Exception {
        mockMvc.perform(POST_ADD_CUSTOMER_VALID_DATA);
        mockMvc.perform(POST_ADD_CUSTOMER_VALID_DATA);

        List<Customer> customers = customerRepository.findAll();

        assertEquals("Invalid number of customers created", 2, customers.size());
    }

    @Test
    @WithAnonymousUser
    public void allCustomers_get_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(get(URL_CUSTOMERS_ALL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test
    @WithMockUser
    public void allCustomers_get_withAuthenticatedUser_returnsCorrectViewAndStatus() throws Exception {
        Customer customer = new Customer();
        customer.setName(CUSTOMER_NAME);
        customer.setBirthDate(LocalDate.now());

        customerRepository.save(customer);

        mockMvc.perform(get(URL_CUSTOMERS_ALL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_ALL_CUSTOMERS))
                .andExpect(model().attributeExists(ATTRIBUTE_CUSTOMERS))
                .andExpect(model().attribute(ATTRIBUTE_CUSTOMERS, Matchers.hasSize(equalTo(1))));
    }
}
