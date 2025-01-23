package com.example.caloriesapp;


import com.example.caloriesapp.model.FoodEntry;

import com.example.caloriesapp.repository.FoodEntryRepository;
import com.example.caloriesapp.service.FoodEntryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class FoodEntryServiceTest {

    @Mock
    private FoodEntryRepository foodEntryRepository;

    @InjectMocks
    private FoodEntryService foodEntryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testSave() {
        FoodEntry foodEntry = new FoodEntry();
        foodEntry.setFoodName("Salad");
        foodEntry.setCalorieValue(200);
        foodEntry.setPrice(5.0);

        foodEntryService.save(foodEntry);

        verify(foodEntryRepository, times(1)).save(foodEntry);
    }

    @Test
    public void testGetTotalCaloriesForToday() {
        FoodEntry entry1 = new FoodEntry();
        entry1.setCalorieValue(300);

        FoodEntry entry2 = new FoodEntry();
        entry2.setCalorieValue(400);

        when(foodEntryRepository.findByEntryDate(any(LocalDate.class))).thenReturn(List.of(entry1, entry2));

        double totalCalories = foodEntryService.getTotalCaloriesForToday();

        assertEquals(700, totalCalories);
        verify(foodEntryRepository, times(1)).findByEntryDate(any(LocalDate.class));
    }

    @Test
    public void testGetMonthlyExpenditure() {
        FoodEntry entry1 = new FoodEntry();
        entry1.setPrice(10.0);
        entry1.setEntryDate(LocalDateTime.of(2025, 1, 1, 12, 0));

        FoodEntry entry2 = new FoodEntry();
        entry2.setPrice(20.0);
        entry2.setEntryDate(LocalDateTime.of(2025, 1, 15, 12, 0));

        when(foodEntryRepository.findAll()).thenReturn(List.of(entry1, entry2));

        double totalExpenditure = foodEntryService.getMonthlyExpenditure(2025, 1);

        assertEquals(30.0, totalExpenditure);
        verify(foodEntryRepository, times(1)).findAll();
    }

    @Test
    public void testGetDaysExceedingCalorieThreshold() {
        when(foodEntryRepository.findDaysExceedingCalorieThreshold(2500.0)).thenReturn(List.of(java.sql.Date.valueOf("2025-01-01")));

        List<LocalDate> result = foodEntryService.getDaysExceedingCalorieThreshold(2500.0);

        assertEquals(1, result.size());
        assertEquals(LocalDate.of(2025, 1, 1), result.get(0));
        verify(foodEntryRepository, times(1)).findDaysExceedingCalorieThreshold(2500.0);
    }


    @Test
    public void testFilterFoodEntriesByDateRange() {
        FoodEntry entry1 = new FoodEntry();
        entry1.setFoodName("Pizza");

        FoodEntry entry2 = new FoodEntry();
        entry2.setFoodName("Burger");

        when(foodEntryRepository.findByEntryDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(List.of(entry1, entry2));

        List<FoodEntry> result = foodEntryService.filterFoodEntriesByDateRange(LocalDate.now().minusDays(7), LocalDate.now());

        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getFoodName());
        verify(foodEntryRepository, times(1)).findByEntryDateBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void testGetEntryCountForDateRange() {
        when(foodEntryRepository.countEntriesByDateRange(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(5L);

        long count = foodEntryService.getEntryCountForDateRange(LocalDateTime.now().minusDays(7), LocalDateTime.now());

        assertEquals(5, count);
        verify(foodEntryRepository, times(1)).countEntriesByDateRange(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    public void testGetAverageCaloriesForDateRange() {
        when(foodEntryRepository.findAverageCaloriesByDateRange(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(400.0);

        double averageCalories = foodEntryService.getAverageCaloriesForDateRange(LocalDateTime.now().minusDays(7), LocalDateTime.now());

        assertEquals(400.0, averageCalories);
        verify(foodEntryRepository, times(1)).findAverageCaloriesByDateRange(any(LocalDateTime.class), any(LocalDateTime.class));
    }
}