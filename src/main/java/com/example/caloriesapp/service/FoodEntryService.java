
package com.example.caloriesapp.service;
import com.example.caloriesapp.model.foodEntry;
import com.example.caloriesapp.repository.FoodEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FoodEntryService {

    @Autowired
    private FoodEntryRepository foodEntryRepository;

    public foodEntry addFoodEntry(String foodName, double calorieValue) {
        foodEntry foodEntry = new foodEntry(foodName, calorieValue, LocalDateTime.now());
        return foodEntryRepository.save(foodEntry);
    }
}
