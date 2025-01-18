package com.example.caloriesapp.service;

import com.example.caloriesapp.model.DailyCalories;
import com.example.caloriesapp.model.WeeklySummary;
import com.example.caloriesapp.model.FoodEntry;
import com.example.caloriesapp.repository.FoodEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodEntryService {

    @Autowired
    private FoodEntryRepository foodEntryRepository;

    // Shto një hyrje të re të ushqimit
    public void addFoodEntry(String foodName, double calorieValue, double price) {
        FoodEntry foodEntry = new FoodEntry(foodName, calorieValue, price, LocalDateTime.now());
        foodEntryRepository.save(foodEntry);
    }

    // Merr totalin e kalorive për sot
    public double getTotalCaloriesForToday() {
        LocalDate today = LocalDate.now();
        List<FoodEntry> todayEntries = foodEntryRepository.findByEntryDate(today);

        return todayEntries.stream()
                .mapToDouble(FoodEntry::getCalorieValue)
                .sum();
    }

    // Merr shpenzimet mujore
    public double getMonthlyExpenditure(int year, int month) {
        List<FoodEntry> monthlyEntries = foodEntryRepository.findAll()
                .stream()
                .filter(entry -> entry.getEntryDate().getYear() == year && entry.getEntryDate().getMonthValue() == month)
                .collect(Collectors.toList());

        return monthlyEntries.stream()
                .mapToDouble(FoodEntry::getPrice)
                .sum();
    }

    // Merr ditët që tejkalojnë pragun e kalorive
    public List<LocalDate> getDaysExceedingCalorieThreshold(double dailyCalorieThreshold) {
        List<java.sql.Date> sqlDates = foodEntryRepository.findDaysExceedingCalorieThreshold(dailyCalorieThreshold);
        return sqlDates.stream()
                .map(java.sql.Date::toLocalDate)
                .collect(Collectors.toList());
    }

    // Merr raportin javor
    public WeeklySummary getWeeklySummary() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);

        // Merr totalin e kalorive për çdo ditë të javës
        List<DailyCalories> totalCaloriesPerDay = foodEntryRepository.getTotalCaloriesPerDayForWeek(startDate, endDate);

        // Numëro ditët kur pragu i kalorive u tejkalua
        long daysThresholdExceeded = totalCaloriesPerDay.stream()
                .filter(dailyCalories -> dailyCalories.getTotalCalories() > 2500) // Përdor getTotalCalories
                .count();

        // Llogarit shpenzimet totale për javën
        double totalExpenditure = foodEntryRepository.getTotalExpenditureForWeek(startDate, endDate);

        // Krijo dhe kthe raportin javor
        return new WeeklySummary(totalCaloriesPerDay, daysThresholdExceeded, totalExpenditure);
    }

    // Funksionaliteti i ri: Filtrimi i hyrjeve ushqimore sipas intervalit të datave
    public List<FoodEntry> filterFoodEntriesByDateRange(LocalDate startDate, LocalDate endDate) {
        // Thirr repository për të marrë hyrjet e filtruara
        return foodEntryRepository.findByEntryDateBetween(startDate, endDate);
    }

}