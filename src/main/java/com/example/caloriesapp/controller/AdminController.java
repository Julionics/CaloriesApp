package com.example.caloriesapp.controller;

import com.example.caloriesapp.model.FoodEntry;
import com.example.caloriesapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller // Për Thymeleaf dhe shfaqje të faqeve HTML
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // Kërkon rolin ADMIN për këto endpoint-e
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Shfaq faqen e menaxhimit ushqimor për adminët
    @GetMapping("/food-management")
    public String showAdminFoodManagementPage() {
        return "adminFoodManagement"; // Emri i skedarit HTML në dosjen templates
    }

    // Merr të gjitha hyrjet ushqimore (CRUD: READ)
    @GetMapping("/entries")
    @ResponseBody
    public ResponseEntity<List<FoodEntry>> getAllFoodEntries() {
        List<FoodEntry> entries = adminService.getAllFoodEntries();
        return ResponseEntity.ok(entries);
    }


    // Shto një hyrje të re ushqimore (CRUD: CREATE)
    @PostMapping("/entries")
    @ResponseBody
    public ResponseEntity<String> createFoodEntry(@RequestBody FoodEntry foodEntry) {
        try {
            adminService.addFoodEntry(foodEntry);
            return ResponseEntity.ok("Food entry created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // Përditëso një hyrje ekzistuese (CRUD: UPDATE)
    @PutMapping("/entries/{id}")
    @ResponseBody
    public ResponseEntity<String> updateFoodEntry(
            @PathVariable Long id,
            @RequestBody FoodEntry foodEntry) {
        adminService.updateFoodEntry(id, foodEntry);
        return ResponseEntity.ok("Food entry updated successfully.");
    }

    // Fshi një hyrje ushqimore (CRUD: DELETE)
    @DeleteMapping("/entries/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteFoodEntry(@PathVariable Long id) {
        try {
            adminService.deleteFoodEntry(id);
            return ResponseEntity.ok("Food entry deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }


    // Merr numrin e hyrjeve për një interval datash
    @GetMapping("/entries/count")
    @ResponseBody
    public ResponseEntity<Long> getEntryCount(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        long count = adminService.getEntryCountForDateRange(startDate, endDate);
        return ResponseEntity.ok(count);
    }

    // Merr mesataren e kalorive për një interval datash
    @GetMapping("/entries/average-calories")
    @ResponseBody
    public ResponseEntity<Double> getAverageCalories(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        Double averageCalories = adminService.getAverageCaloriesForDateRange(startDate, endDate);
        return ResponseEntity.ok(averageCalories);
    }

    // Merr përdoruesit që tejkalojnë limitin mujor të shpenzimeve
    @GetMapping("/users/exceeded-monthly-limit")
    @ResponseBody
    public ResponseEntity<List<String>> getUsersExceedingMonthlyLimit(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam double limit) {
        List<String> users = adminService.getUsersExceedingMonthlyLimit(year, month, limit);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/report")
    @ResponseBody
    public ResponseEntity<?> getAdminReport() {
        return ResponseEntity.ok(adminService.getAdminReport());
    }

}
