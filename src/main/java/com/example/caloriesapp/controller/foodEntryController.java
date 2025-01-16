package com.example.caloriesapp.controller;

import com.example.caloriesapp.model.foodEntry;
import com.example.caloriesapp.service.FoodEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FoodEntryController {

    @Autowired
    private FoodEntryService foodEntryService;

    // Endpoint për të shfaqur formën për shtimin e ushqimeve
    @GetMapping("/food-entry")
    public String showFoodEntryForm() {
        return "foodEntryForm";
    }

    // Endpoint për të shtuar një hyrje të re ushqimi
    @PostMapping("/food-entry")
    public String addFoodEntry(@RequestParam String foodName,
                               @RequestParam double calorieValue,
                               @RequestParam double price,
                               Model model) {
        // Shto një hyrje të re nëpërmjet shërbimit
        foodEntry foodEntry = foodEntryService.addFoodEntry(foodName, calorieValue, price);

        // Dërgo një mesazh te view për suksesin e operacionit
        model.addAttribute("message", "Hyrja e ushqimit u shtua me sukses!");
        return "foodEntryForm";
    }
}



