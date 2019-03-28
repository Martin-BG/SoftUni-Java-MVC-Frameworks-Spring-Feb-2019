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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
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

    private static final String URL_USERS_LOGIN = "/users/login";
    private static final String URL_USERS_REGISTER = "/users/register";

    private static final String REDIRECT_URL_PATTERN_LOGIN = "**/login";

    private static final String VIEW_LOGIN = "login";
    private static final String VIEW_REGISTER = "register";

    private static final String USER_USERNAME = "username";
    private static final String USER_EMAIL = "mail@email.com";
    private static final String USER_PASSWORD = "password";
    private static final String USER_WRONG_CONFIRM_PASSWORD = "another password";

    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_CONFIRM_PASSWORD = "confirmPassword";
    private static final String PARAM_EMAIL = "email";

    private final static MockHttpServletRequestBuilder POST_USER_VALID_DATA = post(URL_USERS_REGISTER)
            .param(PARAM_USERNAME, USER_USERNAME)
            .param(PARAM_PASSWORD, USER_PASSWORD)
            .param(PARAM_CONFIRM_PASSWORD, USER_PASSWORD)
            .param(PARAM_EMAIL, USER_EMAIL);

    private final static MockHttpServletRequestBuilder POST_USER_WRONG_CONFIRM_PASSWORD = post(URL_USERS_REGISTER)
            .param(PARAM_USERNAME, USER_USERNAME)
            .param(PARAM_PASSWORD, USER_PASSWORD)
            .param(PARAM_CONFIRM_PASSWORD, USER_WRONG_CONFIRM_PASSWORD)
            .param(PARAM_EMAIL, USER_EMAIL);

    private static final String EMPTY_VALUE = "";
    private final static MockHttpServletRequestBuilder POST_USER_EMPTY_FIELDS = post(URL_USERS_REGISTER)
            .param(PARAM_USERNAME, EMPTY_VALUE)
            .param(PARAM_PASSWORD, EMPTY_VALUE)
            .param(PARAM_CONFIRM_PASSWORD, EMPTY_VALUE)
            .param(PARAM_EMAIL, EMPTY_VALUE);

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
        mockMvc.perform(get(URL_USERS_LOGIN))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_LOGIN));
    }

    @Test
    @WithMockUser
    public void login_withAuthenticatedUser_isForbidden() throws Exception {
        mockMvc.perform(get(URL_USERS_LOGIN))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void register_get_withAnonymousUser_returnsCorrectViewAndStatus() throws Exception {
        mockMvc.perform(get(URL_USERS_REGISTER))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_REGISTER));
    }

    @Test
    @WithMockUser
    public void register_get_withAuthenticatedUser_isForbidden() throws Exception {
        mockMvc.perform(get(URL_USERS_REGISTER))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void register_post_withAuthenticatedUser_isForbidden() throws Exception {
        mockMvc.perform(post(URL_USERS_REGISTER))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void register_post_validDataWithAnonymousUser_returnsCorrectViewAndStatus() throws Exception {
        mockMvc.perform(POST_USER_VALID_DATA)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }

    @Test
    @WithAnonymousUser
    public void register_post_validDataWithAnonymousUser_registersNewUser() throws Exception {
        mockMvc.perform(POST_USER_VALID_DATA);

        User user = userRepository.findByUsername(USER_USERNAME).orElseThrow();
        assertNotNull(user.getId());
        assertFalse(user.getId().isEmpty());
        assertEquals(USER_USERNAME, user.getUsername());
        assertEquals(USER_EMAIL, user.getEmail());
        assertTrue(passwordEncoder.matches(USER_PASSWORD, user.getPassword()));
    }

    @Test(expected = NestedServletException.class)
    @WithAnonymousUser
    public void register_post_duplicateUsernameWithAnonymousUser_throwsException() throws Exception {
        mockMvc.perform(POST_USER_VALID_DATA);
        mockMvc.perform(POST_USER_VALID_DATA);
    }


    @Test(expected = Exception.class)
    @WithAnonymousUser
    public void register_post_invalidConfirmPasswordWithAnonymousUser_throwsException() throws Exception {
        // This test exposes a bug in controller/service logic
        // as password is never checked against confirmPassword
        mockMvc.perform(POST_USER_WRONG_CONFIRM_PASSWORD);
    }

    @Test(expected = Exception.class)
    @WithAnonymousUser
    public void register_post_emptyFieldsWithAnonymousUser_throwsException() throws Exception {
        // This test exposes a bug in controller/service logic
        // as input data is not validated at all (empty fields accepted)
        mockMvc.perform(POST_USER_EMPTY_FIELDS);
    }
}