
package com.example.caloriesapp;

import com.example.caloriesapp.controller.FoodEntryController;
import com.example.caloriesapp.model.FoodEntry;
import com.example.caloriesapp.model.WeeklySummary;
import com.example.caloriesapp.service.FoodEntryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FoodEntryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FoodEntryService foodEntryService;

    @InjectMocks
    private FoodEntryController foodEntryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(foodEntryController).build();
    }

    @Test
    public void testShowDefaultPage() throws Exception {
        mockMvc.perform(get("/food-entry"))
                .andExpect(status().isOk())
                .andExpect(view().name("foodEntryForm"));
    }

    @Test
    public void testShowFoodEntryForm() throws Exception {
        double dailyCalorieThreshold = 2500;
        double monthlyExpenditureThreshold = 1000;

        when(foodEntryService.getDaysExceedingCalorieThreshold(dailyCalorieThreshold))
                .thenReturn(List.of(LocalDate.now()));
        when(foodEntryService.getMonthlyExpenditure(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue()))
                .thenReturn(1200.0);
        when(foodEntryService.getWeeklySummary()).thenReturn(new WeeklySummary());

        mockMvc.perform(get("/food-entry/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("foodEntryForm"))
                .andExpect(model().attributeExists("exceededDays"))
                .andExpect(model().attributeExists("monthlyExpenditure"))
                .andExpect(model().attributeExists("monthlyExpenditureThreshold"))
                .andExpect(model().attributeExists("weeklySummary"));

        verify(foodEntryService, times(1)).getDaysExceedingCalorieThreshold(dailyCalorieThreshold);
        verify(foodEntryService, times(1)).getMonthlyExpenditure(anyInt(), anyInt());
        verify(foodEntryService, times(1)).getWeeklySummary();
    }

    @Test
    public void testAddFoodEntry() throws Exception {
        mockMvc.perform(post("/food-entry/new")
                        .param("foodName", "Apple")
                        .param("calorieValue", "95.0")
                        .param("price", "0.5"))
                .andExpect(status().isOk())
                .andExpect(view().name("foodEntryForm"))
                .andExpect(model().attributeExists("successMessage"));

        // Kontrollojmë që nuk u thirr një metodë specifike në FoodEntryService
        verify(foodEntryService, times(0)).save(any(FoodEntry.class));
    }

    @Test
    public void testFilterFoodEntriesByDateRange() throws Exception {
        when(foodEntryService.filterFoodEntriesByDateRange(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(new FoodEntry("Apple", 95.0, 0.5, LocalDateTime.now(), null)));

        mockMvc.perform(get("/food-entry/filter")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-31"))
                .andExpect(status().isOk())
                .andExpect(view().name("filteredFoodEntries"))
                .andExpect(model().attributeExists("filteredEntries"))
                .andExpect(model().attribute("startDate", LocalDate.parse("2025-01-01")))
                .andExpect(model().attribute("endDate", LocalDate.parse("2025-01-31")));

        verify(foodEntryService, times(1)).filterFoodEntriesByDateRange(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void testShowWeeklySummary() throws Exception {
        when(foodEntryService.getWeeklySummary()).thenReturn(new WeeklySummary());

        mockMvc.perform(get("/food-entry/weekly-summary"))
                .andExpect(status().isOk())
                .andExpect(view().name("weeklySummaryView"))
                .andExpect(model().attributeExists("weeklySummary"));

        verify(foodEntryService, times(1)).getWeeklySummary();
    }

    @Test
    public void testBackToMain() throws Exception {
        mockMvc.perform(get("/food-entry/back-to-main"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/food-entry/new"));
    }
}