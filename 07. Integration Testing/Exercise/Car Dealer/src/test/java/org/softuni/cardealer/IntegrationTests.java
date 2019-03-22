package org.softuni.cardealer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.models.binding.AddSupplierBindingModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class IntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SupplierRepository supplierRepository;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser("spring")
    public void test2() throws Exception {
        this.mockMvc
                .perform(get("/cars/all"))
                .andExpect(view().name("all-cars"));

        this.mockMvc
                .perform(get("/cars/all"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    @WithMockUser("spring")
    public void test3() throws Exception {
        AddSupplierBindingModel model = new AddSupplierBindingModel() {{
            setName("Megaparts");
            setIsImporter(true);
        }};

        this.mockMvc
                .perform(post("/suppliers/add")
                        .param("name", "Megaparts")
                        .param("isImporter", "on"));

        Assert.assertTrue(this.supplierRepository.count() == 1);
    }
}
