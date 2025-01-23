package com.example.caloriesapp;

import com.example.caloriesapp.model.User;
import com.example.caloriesapp.repository.UserRepository;
import com.example.caloriesapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        // Mock për një email që nuk ekziston
        when(userRepository.findByEmail(eq("test@example.com"))).thenReturn(Optional.empty());

        // Mock për kodimin e fjalëkalimit
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Mock për ruajtjen e përdoruesit
        User mockUser = new User();
        mockUser.setName("Test User");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setRole("ROLE_USER");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Thirr metoda për testim
        User registeredUser = userService.registerUser("Test User", "test@example.com", "password", "ROLE_USER");

        // Verifikimet
        assertNotNull(registeredUser);
        assertEquals("Test User", registeredUser.getName());
        assertEquals("test@example.com", registeredUser.getEmail());
        assertEquals("encodedPassword", registeredUser.getPassword());
        assertEquals("ROLE_USER", registeredUser.getRole());

        // Verifikoni thirrjet e metodave
        verify(userRepository, times(1)).findByEmail(eq("test@example.com"));
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegisterUser_EmailAlreadyExists() {
        // Mock për një email që ekziston
        when(userRepository.findByEmail(eq("test@example.com"))).thenReturn(Optional.of(new User()));

        // Prisni një përjashtim kur metoda thirret
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser("Test User", "test@example.com", "password", "ROLE_USER");
        });

        // Kontrolloni mesazhin e përjashtimit
        assertEquals("Email already exists", exception.getMessage());

        // Verifikoni që userRepository.save nuk është thirrur
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegisterUser_NullOrEmptyFields() {
        // Test për raste ku fushat janë bosh ose null
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser("", "test@example.com", "password", "ROLE_USER");
        });

        assertNotNull(exception);

        // Këtu mund të kontrolloni validimet sipas implementimit tuaj
    }
}
