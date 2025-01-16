package com.example.caloriesapp.controller;

import com.example.caloriesapp.model.foodEntry;
import com.example.caloriesapp.service.FoodEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/food-entry")
public class FoodEntryController {

    @Autowired
    private FoodEntryService foodEntryService;

    // Endpoint për të shfaqur formën për shtimin e ushqimeve
    @GetMapping
    public String showFoodEntryForm() {
        return "foodEntryForm";
    }

    // Endpoint për të shtuar një hyrje të re ushqimi
    @PostMapping
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

    // Endpoint për të filtruar hyrjet e ushqimeve sipas një intervali datash
    @GetMapping("/filter")
    @ResponseBody
    public List<foodEntry> filterFoodEntries(@RequestParam LocalDate startDate,
                                             @RequestParam LocalDate endDate) {
        return foodEntryService.filterEntriesByDateRange(startDate, endDate);
    }
}
