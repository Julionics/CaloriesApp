package com.example.caloriesapp.service;

import com.example.caloriesapp.model.FoodEntry;
import com.example.caloriesapp.model.User;
import com.example.caloriesapp.repository.FoodEntryRepository;
import com.example.caloriesapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final FoodEntryRepository foodEntryRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminService(FoodEntryRepository foodEntryRepository, UserRepository userRepository) {
        this.foodEntryRepository = foodEntryRepository;
        this.userRepository = userRepository;
    }

    public List<FoodEntry> getAllFoodEntries() {
        return foodEntryRepository.findAll();
    }

    public void addFoodEntry(FoodEntry foodEntry) {
        User user = userRepository.findByEmail("admin@example.com")
                .orElseThrow(() -> new RuntimeException("Admin user not found in the database"));

        foodEntry.setUser(user);
        foodEntryRepository.save(foodEntry);
    }

    public void updateFoodEntry(Long id, FoodEntry updatedFoodEntry) {
        FoodEntry existingFoodEntry = foodEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food entry not found."));
        existingFoodEntry.setFoodName(updatedFoodEntry.getFoodName());
        existingFoodEntry.setCalorieValue(updatedFoodEntry.getCalorieValue());
        existingFoodEntry.setPrice(updatedFoodEntry.getPrice());
        foodEntryRepository.save(existingFoodEntry);
    }

    public void deleteFoodEntry(Long id) {
        if (!foodEntryRepository.existsById(id)) {
            throw new RuntimeException("Food entry not found with id: " + id);
        }
        foodEntryRepository.deleteById(id);
    }

    public String getAdminReport() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last7Days = now.minusDays(7);
        LocalDateTime weekBeforeLast7Days = last7Days.minusDays(7);
        LocalDateTime startOfPreviousMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDateTime endOfPreviousMonth = now.withDayOfMonth(1).minusDays(1);

        // Numri i hyrjeve në 7 ditët e fundit
        long entriesLast7Days = foodEntryRepository.countByEntryDateBetween(last7Days, now);

        // Numri i hyrjeve javën para 7 ditëve të fundit
        long entriesWeekBefore = foodEntryRepository.countByEntryDateBetween(weekBeforeLast7Days, last7Days);

        // Mesatarja e kalorive në 7 ditët e fundit
        Double averageCaloriesLast7Days = foodEntryRepository.findAverageCaloriesByDateRange(last7Days, now);
        if (averageCaloriesLast7Days == null) {
            averageCaloriesLast7Days = 0.0;
        }

        // Përdoruesit që tejkalojnë limitin mujor në muajin e kaluar
        List<User> usersExceedingMonthlyLimit = userRepository.findUsersExceedingMonthlyLimit(startOfPreviousMonth, endOfPreviousMonth, 1000.0);

        // Kthe të dhënat si një hartë
        Map<String, Object> report = new HashMap<>();
        report.put("entriesLast7Days", entriesLast7Days);
        report.put("entriesWeekBefore", entriesWeekBefore);
        report.put("averageCaloriesLast7Days", averageCaloriesLast7Days);
        report.put("usersExceedingMonthlyLimit", usersExceedingMonthlyLimit);

        return "report";
    }

    public long getEntryCountForDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return 0;
    }

    public Double getAverageCaloriesForDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return 0.0;
    }

    public List<String> getUsersExceedingMonthlyLimit(int year, int month, double limit) {
        return List.of();
    }
}
