package com.example.caloriesapp.service;

import com.example.caloriesapp.model.foodEntry;
import com.example.caloriesapp.model.WeeklySummary;
import com.example.caloriesapp.repository.FoodEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FoodEntryService {

    @Autowired
    private FoodEntryRepository foodEntryRepository;

    // Metoda për të shtuar një hyrje të re të ushqimit
    public foodEntry addFoodEntry(String foodName, double calorieValue, double price) {
        foodEntry foodEntry = new foodEntry(foodName, calorieValue, LocalDateTime.now(), price);
        return foodEntryRepository.save(foodEntry);
    }

    // Metoda për të llogaritur shpenzimet mujore
    public double calculateMonthlyExpenditure(int year, int month) {
        List<foodEntry> entries = foodEntryRepository.findByYearAndMonth(year, month);

        // Llogarit totalin e shpenzimeve
        double totalExpenditure = entries.stream()
                                         .mapToDouble(foodEntry::getPrice)
                                         .sum();

        // Kontrollo nëse shpenzimet tejkalojnë kufirin
        if (totalExpenditure > 1000) {
            System.out.println("WARNING: Monthly expenditure exceeded €1,000!");
        }

        return totalExpenditure;
    }

    // Metoda për të krijuar raportin javor
    public WeeklySummary getWeeklySummary() {
        // Llogarit datën e fillimit dhe të fundit të javës
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);

        // Merr të dhënat për raportin nga repository
        List<Double> totalCaloriesPerDay = foodEntryRepository.getTotalCaloriesPerDayForWeek(startDate, endDate);
        Long daysThresholdExceeded = foodEntryRepository.getDaysCalorieThresholdExceeded(startDate, endDate);
        Double totalExpenditure = foodEntryRepository.getTotalExpenditureForWeek(startDate, endDate);

        // Krijo dhe kthe objektin WeeklySummary
        return new WeeklySummary(totalCaloriesPerDay, daysThresholdExceeded, totalExpenditure);
    }
}



