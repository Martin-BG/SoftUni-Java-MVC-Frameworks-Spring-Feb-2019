package org.softuni.cardealer.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    private static final String URL_INDEX = "/";
    private static final String URL_HOME = "/home";

    private static final String REDIRECT_URL_PATTERN_LOGIN = "**/users/login";

    private static final String VIEW_INDEX = "index";
    private static final String VIEW_HOME = "home";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void index_withAnonymousUser_returnsCorrectViewAndStatus() throws Exception {
        mockMvc.perform(get(URL_INDEX))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_INDEX));
    }

    @Test
    @WithMockUser
    public void index_withAuthenticatedUser_isForbidden() throws Exception {
        mockMvc.perform(get(URL_INDEX))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void home_withAuthenticatedUser_returnsCorrectViewAndStatus() throws Exception {
        mockMvc.perform(get(URL_HOME))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_HOME));
    }

    @Test
    @WithAnonymousUser
    public void home_withAnonymousUser_redirectsToLoginPage() throws Exception {
        mockMvc.perform(get(URL_HOME))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(REDIRECT_URL_PATTERN_LOGIN));
    }
}
