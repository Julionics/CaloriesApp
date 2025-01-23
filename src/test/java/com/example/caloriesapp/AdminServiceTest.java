package com.example.caloriesapp;

import com.example.caloriesapp.model.FoodEntry;
import com.example.caloriesapp.model.User;
import com.example.caloriesapp.repository.FoodEntryRepository;
import com.example.caloriesapp.repository.UserRepository;
import com.example.caloriesapp.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @Mock
    private FoodEntryRepository foodEntryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFoodEntries() {
        FoodEntry foodEntry1 = new FoodEntry();
        foodEntry1.setFoodName("Pizza");

        FoodEntry foodEntry2 = new FoodEntry();
        foodEntry2.setFoodName("Burger");

        List<FoodEntry> mockFoodEntries = List.of(foodEntry1, foodEntry2);

        when(foodEntryRepository.findAll()).thenReturn(mockFoodEntries);

        List<FoodEntry> result = adminService.getAllFoodEntries();

        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getFoodName());
        assertEquals("Burger", result.get(1).getFoodName());
        verify(foodEntryRepository, times(1)).findAll();
    }

    @Test
    public void testAddFoodEntry() {
        FoodEntry foodEntry = new FoodEntry();
        foodEntry.setFoodName("Salad");

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@example.com");

        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(admin));

        adminService.addFoodEntry(foodEntry);

        assertEquals(admin, foodEntry.getUser());
        verify(foodEntryRepository, times(1)).save(foodEntry);
    }

    @Test
    public void testAddFoodEntryAdminNotFound() {
        FoodEntry foodEntry = new FoodEntry();
        foodEntry.setFoodName("Salad");

        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adminService.addFoodEntry(foodEntry));
        assertEquals("Admin user not found in the database", exception.getMessage());
        verify(foodEntryRepository, never()).save(any(FoodEntry.class));
    }

    @Test
    public void testUpdateFoodEntry() {
        FoodEntry existingFoodEntry = new FoodEntry();
        existingFoodEntry.setId(1L);
        existingFoodEntry.setFoodName("Pizza");

        FoodEntry updatedFoodEntry = new FoodEntry();
        updatedFoodEntry.setFoodName("Burger");
        updatedFoodEntry.setCalorieValue(600);
        updatedFoodEntry.setPrice(12.0);

        when(foodEntryRepository.findById(1L)).thenReturn(Optional.of(existingFoodEntry));

        adminService.updateFoodEntry(1L, updatedFoodEntry);

        assertEquals("Burger", existingFoodEntry.getFoodName());
        assertEquals(600, existingFoodEntry.getCalorieValue());
        assertEquals(12.0, existingFoodEntry.getPrice());
        verify(foodEntryRepository, times(1)).save(existingFoodEntry);
    }

    @Test
    public void testUpdateFoodEntryNotFound() {
        FoodEntry updatedFoodEntry = new FoodEntry();
        updatedFoodEntry.setFoodName("Burger");

        when(foodEntryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adminService.updateFoodEntry(1L, updatedFoodEntry));
        assertEquals("Food entry not found.", exception.getMessage());
        verify(foodEntryRepository, never()).save(any(FoodEntry.class));
    }

    @Test
    public void testDeleteFoodEntry() {
        when(foodEntryRepository.existsById(1L)).thenReturn(true);

        adminService.deleteFoodEntry(1L);

        verify(foodEntryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteFoodEntryNotFound() {
        when(foodEntryRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> adminService.deleteFoodEntry(1L));
        assertEquals("Food entry not found with id: 1", exception.getMessage());
        verify(foodEntryRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testGetAdminReport() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last7Days = now.minusDays(7);
        LocalDateTime startOfPreviousMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDateTime endOfPreviousMonth = now.withDayOfMonth(1).minusDays(1);

        when(foodEntryRepository.countByEntryDateBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(10L);
        when(foodEntryRepository.findAverageCaloriesByDateRange(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(500.0);
        when(userRepository.findUsersExceedingMonthlyLimit(any(LocalDateTime.class), any(LocalDateTime.class), eq(1000.0)))
                .thenReturn(List.of(new User("John Doe", "john@example.com", "password")));

        String report = adminService.getAdminReport();

        assertNotNull(report);
        verify(foodEntryRepository, times(2)).countByEntryDateBetween(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(foodEntryRepository, times(1)).findAverageCaloriesByDateRange(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(userRepository, times(1)).findUsersExceedingMonthlyLimit(any(LocalDateTime.class), any(LocalDateTime.class), eq(1000.0));
    }
}
