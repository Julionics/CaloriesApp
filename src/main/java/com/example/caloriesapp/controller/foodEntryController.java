package com.example.caloriesapp.controller;

import com.example.caloriesapp.model.foodEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.caloriesapp.service.FoodEntryService;

@Controller

public class foodEntryController {

    @Autowired
    private FoodEntryService foodEntryService;

    @GetMapping("/food-entry")
    public String showFoodEntryForm() {
        return "foodEntryForm";
    }

    @PostMapping("/food-entry")
    public String addFoodEntry(@RequestParam String foodName,
                               @RequestParam double calorieValue,
                               Model model) {
        foodEntry foodEntry = foodEntryService.addFoodEntry(foodName, calorieValue);
        model.addAttribute("message", "Hyrja e ushqimit u shtua me sukses!");
        return "foodEntryForm";
    }
}


