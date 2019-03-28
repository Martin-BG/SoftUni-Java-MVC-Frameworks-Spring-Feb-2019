package org.softuni.cardealer.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.User;
import org.softuni.cardealer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Before
    public void init() {
        userRepository.deleteAll();
    }

    @Test
    @WithAnonymousUser
    public void login_withAnonymousUser_returnsCorrectViewAndStatus() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser
    public void login_withAuthenticatedUser_isForbidden() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void register_get_WithAnonymousUser_returnsCorrectViewAndStatus() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @WithMockUser
    public void register_get_WithAuthenticatedUser_isForbidden() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void register_post_WithAuthenticatedUser_isForbidden() throws Exception {
        mockMvc.perform(post("/users/register"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void register_post_ValidDataWithAnonymousUser_returnsCorrectViewAndStatus() throws Exception {
        mockMvc
                .perform(
                        post("/users/register")
                                .param("username", "pesho")
                                .param("password", "pws")
                                .param("confirmPassword", "pwd")
                                .param("email", "pesho@mail.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithAnonymousUser
    public void register_post_ValidDataWithAnonymousUser_registersNewUser() throws Exception {
        mockMvc
                .perform(
                        post("/users/register")
                                .param("username", "pesho")
                                .param("password", "pws")
                                .param("confirmPassword", "pwd")
                                .param("email", "pesho@mail.com"));

        User user = userRepository.findByUsername("pesho").orElseThrow();
        assertNotNull(user.getId());
        assertNotEquals("", user.getId());
        assertEquals("pesho", user.getUsername());
        assertEquals("pesho@mail.com", user.getEmail());
        assertTrue(passwordEncoder.matches("pws", user.getPassword()));
    }

    @Test(expected = NestedServletException.class)
    @WithAnonymousUser
    public void register_post_DuplicateUsernameWithAnonymousUser_throwsException() throws Exception {
        mockMvc
                .perform(
                        post("/users/register")
                                .param("username", "pesho")
                                .param("password", "pws")
                                .param("confirmPassword", "pwd")
                                .param("email", "pesho@mail.com"));

        mockMvc
                .perform(
                        post("/users/register")
                                .param("username", "pesho")
                                .param("password", "pws")
                                .param("confirmPassword", "pwd")
                                .param("email", "pesho@mail.com"));
    }
}