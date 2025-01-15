package com.example.caloriesapp.service;

import com.example.caloriesapp.repository.FoodEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CalorieThresholdService {

    @Autowired
    private FoodEntryRepository foodEntryRepository;

    private static final double DAILY_CALORIE_LIMIT = 2500.0;

    public boolean isThresholdExceeded(LocalDate date) {
        Double totalCalories = foodEntryRepository.getTotalCaloriesByDate(date);
        return totalCalories != null && totalCalories > DAILY_CALORIE_LIMIT;
    }
}
