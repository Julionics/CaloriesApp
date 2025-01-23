package com.example.caloriesapp;

import com.example.caloriesapp.model.DailyCalories;
import com.example.caloriesapp.model.WeeklySummary;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeeklySummaryTest {

    @Test
    void testConstructorAndGetters() {
        // Përgatitja e të dhënave hyrëse
        DailyCalories day1 = new DailyCalories(java.sql.Date.valueOf("2025-01-01"), 2000.0);
        DailyCalories day2 = new DailyCalories(java.sql.Date.valueOf("2025-01-02"), 3000.0);
        List<DailyCalories> totalCaloriesPerDay = List.of(day1, day2);
        long daysThresholdExceeded = 1;
        double totalExpenditure = 100.0;

        // Krijimi i objektit WeeklySummary
        WeeklySummary weeklySummary = new WeeklySummary(totalCaloriesPerDay, daysThresholdExceeded, totalExpenditure);

        // Verifikimi i konstruktorit dhe getter-ave
        assertEquals(totalCaloriesPerDay, weeklySummary.getTotalCaloriesPerDay(), "TotalCaloriesPerDay nuk përputhet");
        assertEquals(daysThresholdExceeded, weeklySummary.getDaysThresholdExceeded(), "DaysThresholdExceeded nuk përputhet");
        assertEquals(totalExpenditure, weeklySummary.getTotalExpenditure(), "TotalExpenditure nuk përputhet");
    }

    @Test
    void testSetters() {
        // Krijimi i objektit bosh
        WeeklySummary weeklySummary = new WeeklySummary();

        // Përgatitja e të dhënave për testim
        DailyCalories day1 = new DailyCalories(java.sql.Date.valueOf("2025-01-01"), 2500.0);
        DailyCalories day2 = new DailyCalories(java.sql.Date.valueOf("2025-01-02"), 3500.0);
        List<DailyCalories> newTotalCaloriesPerDay = List.of(day1, day2);
        long newDaysThresholdExceeded = 2;
        double newTotalExpenditure = 200.0;

        // Vendosja e vlerave me setter-a
        weeklySummary.setTotalCaloriesPerDay(newTotalCaloriesPerDay);
        weeklySummary.setDaysThresholdExceeded(newDaysThresholdExceeded);
        weeklySummary.setTotalExpenditure(newTotalExpenditure);

        // Verifikimi i setter-ave
        assertEquals(newTotalCaloriesPerDay, weeklySummary.getTotalCaloriesPerDay(), "Setter për TotalCaloriesPerDay nuk funksionon siç duhet");
        assertEquals(newDaysThresholdExceeded, weeklySummary.getDaysThresholdExceeded(), "Setter për DaysThresholdExceeded nuk funksionon siç duhet");
        assertEquals(newTotalExpenditure, weeklySummary.getTotalExpenditure(), "Setter për TotalExpenditure nuk funksionon siç duhet");
    }
}