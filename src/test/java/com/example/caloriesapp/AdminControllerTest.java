package com.example.caloriesapp;

import com.example.caloriesapp.controller.AdminController;
import com.example.caloriesapp.model.FoodEntry;
import com.example.caloriesapp.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void testShowAdminFoodManagementPage() throws Exception {
        mockMvc.perform(get("/admin/food-management"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminFoodManagement"));
    }

    @Test
    public void testGetAllFoodEntries() throws Exception {
        List<FoodEntry> foodEntries = List.of(
                new FoodEntry("Apple", 95.0, 0.5, LocalDateTime.now(), null),
                new FoodEntry("Banana", 105.0, 0.3, LocalDateTime.now(), null)
        );

        when(adminService.getAllFoodEntries()).thenReturn(foodEntries);

        mockMvc.perform(get("/admin/entries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].foodName").value("Apple"))
                .andExpect(jsonPath("$[1].foodName").value("Banana"));

        verify(adminService, times(1)).getAllFoodEntries();
    }

    @Test
    public void testCreateFoodEntry() throws Exception {
        mockMvc.perform(post("/admin/entries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "foodName": "Apple",
                                    "calorieValue": 95.0,
                                    "price": 0.5
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Food entry created successfully."));

        verify(adminService, times(1)).addFoodEntry(any(FoodEntry.class));
    }

    @Test
    public void testUpdateFoodEntry() throws Exception {
        mockMvc.perform(put("/admin/entries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "foodName": "Updated Food",
                                    "calorieValue": 100.0,
                                    "price": 1.0
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Food entry updated successfully."));

        verify(adminService, times(1)).updateFoodEntry(eq(1L), any(FoodEntry.class));
    }

    @Test
    public void testDeleteFoodEntry() throws Exception {
        mockMvc.perform(delete("/admin/entries/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Food entry deleted successfully."));

        verify(adminService, times(1)).deleteFoodEntry(1L);
    }

    @Test
    public void testGetEntryCount() throws Exception {
        when(adminService.getEntryCountForDateRange(any(), any())).thenReturn(10L);

        mockMvc.perform(get("/admin/entries/count")
                        .param("startDate", "2025-01-01T00:00:00")
                        .param("endDate", "2025-01-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));

        verify(adminService, times(1)).getEntryCountForDateRange(any(), any());
    }

    @Test
    public void testGetAverageCalories() throws Exception {
        when(adminService.getAverageCaloriesForDateRange(any(), any())).thenReturn(150.5);

        mockMvc.perform(get("/admin/entries/average-calories")
                        .param("startDate", "2025-01-01T00:00:00")
                        .param("endDate", "2025-01-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(content().string("150.5"));

        verify(adminService, times(1)).getAverageCaloriesForDateRange(any(), any());
    }

    @Test
    public void testGetUsersExceedingMonthlyLimit() throws Exception {
        when(adminService.getUsersExceedingMonthlyLimit(2025, 1, 1000.0))
                .thenReturn(List.of("John Doe", "Jane Doe"));

        mockMvc.perform(get("/admin/users/exceeded-monthly-limit")
                        .param("year", "2025")
                        .param("month", "1")
                        .param("limit", "1000.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value("John Doe"))
                .andExpect(jsonPath("$[1]").value("Jane Doe"));

        verify(adminService, times(1)).getUsersExceedingMonthlyLimit(2025, 1, 1000.0);
    }

    @Test
    public void testGetAdminReport() throws Exception {
        when(adminService.getAdminReport()).thenReturn("Sample Report");

        mockMvc.perform(get("/admin/report"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sample Report"));

        verify(adminService, times(1)).getAdminReport();
    }
}
