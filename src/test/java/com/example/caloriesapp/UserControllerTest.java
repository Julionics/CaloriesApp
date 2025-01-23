package com.example.caloriesapp;

import com.example.caloriesapp.controller.UserController;
import com.example.caloriesapp.model.User;
import com.example.caloriesapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testShowSignUpForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk()) // Kontrollon që statusi është 200 OK
                .andExpect(view().name("sign-up")); // Kontrollon që pamja është "sign-up"
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        // Mockimi i metodës registerUser
        when(userService.registerUser(anyString(), anyString(), anyString()))
                .thenReturn(new User("Test User", "test@example.com", "password"));

        mockMvc.perform(post("/register")
                        .param("name", "Test User")
                        .param("email", "test@example.com")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection()) // Kontrollon që është një ridrejtim
                .andExpect(redirectedUrl("/food-entry/new")); // Kontrollon që ridrejtohet në faqen e saktë

        // Verifikon që metoda registerUser u thirr një herë me parametrat e dhënë
        verify(userService, times(1)).registerUser("Test User", "test@example.com", "password");
    }

    @Test
    public void testRegisterUserFailure() throws Exception {
        // Simulon një përjashtim nga metoda registerUser
        when(userService.registerUser(anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Email already exists"));

        mockMvc.perform(post("/register")
                        .param("name", "Test User")
                        .param("email", "test@example.com")
                        .param("password", "password"))
                .andExpect(status().isOk()) // Kontrollon që statusi është 200 OK
                .andExpect(view().name("sign-up")) // Kontrollon që rikthehet te "sign-up"
                .andExpect(model().attributeExists("error")) // Kontrollon që ekziston mesazhi i gabimit
                .andExpect(model().attribute("error", "Email already exists")); // Kontrollon përmbajtjen e mesazhit

        // Verifikon që metoda registerUser u thirr një herë
        verify(userService, times(1)).registerUser("Test User", "test@example.com", "password");
    }
}
