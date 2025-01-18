package com.example.caloriesapp.controller;

import com.example.caloriesapp.model.FoodEntry;
import com.example.caloriesapp.model.WeeklySummary;
import com.example.caloriesapp.service.FoodEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/food-entry")
public class FoodEntryController {

    @Autowired
    private FoodEntryService foodEntryService;

    @GetMapping("/new")
    public String showFoodEntryForm(Model model) {
        double dailyCalorieThreshold = 2500;
        double monthlyExpenditureThreshold = 1000;

        // Merr ditët që tejkalojnë pragun e kalorive
        List<LocalDate> exceededDays = foodEntryService.getDaysExceedingCalorieThreshold(dailyCalorieThreshold);

        // Merr shpenzimet mujore
        double monthlyExpenditure = foodEntryService.getMonthlyExpenditure(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue());

        // Merr raportin javor
        WeeklySummary weeklySummary = foodEntryService.getWeeklySummary();

        model.addAttribute("exceededDays", exceededDays);
        model.addAttribute("threshold", dailyCalorieThreshold);
        model.addAttribute("monthlyExpenditure", monthlyExpenditure);
        model.addAttribute("monthlyExpenditureThreshold", monthlyExpenditureThreshold);
        model.addAttribute("weeklySummary", weeklySummary);

        if (monthlyExpenditure > monthlyExpenditureThreshold) {
            model.addAttribute("expenditureWarning", "Shpenzimet mujore tejkalojnë pragun prej €1,000!");
        }

        return "foodEntryForm"; // Skedari kryesor HTML
    }

    @PostMapping("/new")
    public String addFoodEntry(@RequestParam String foodName,
                               @RequestParam double calorieValue,
                               @RequestParam double price,
                               Model model) {
        try {
            // Shto një hyrje të re ushqimi
            foodEntryService.addFoodEntry(foodName, calorieValue, price);

            // Rikthe formularin me përditësimet e reja
            return "redirect:/food-entry/new"; // Përdor redirect për të rifreskuar
        } catch (Exception e) {
            model.addAttribute("error", "Diçka shkoi keq: " + e.getMessage());
            return "foodEntryForm";
        }
    }

    @GetMapping("/filter")
    public String filterFoodEntriesByDateRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                               Model model) {
        // Thirr shërbimin për të marrë hyrjet e filtruara
        List<FoodEntry> filteredEntries = foodEntryService.filterFoodEntriesByDateRange(startDate, endDate);

        // Shto të dhënat në model për shfaqje
        model.addAttribute("filteredEntries", filteredEntries);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "filteredFoodEntries"; // Skedari HTML që shfaq hyrjet e filtruara
    }


    @GetMapping("/weekly-summary")
    public String showWeeklySummary(Model model) {
        WeeklySummary weeklySummary = foodEntryService.getWeeklySummary();
        model.addAttribute("weeklySummary", weeklySummary);

        return "weeklySummaryView"; // Emri i skedarit HTML për raportin javor
    }

    @GetMapping("/back-to-main")
    public String backToMain() {
        return "redirect:/food-entry/new"; // Kthehet në faqen kryesore
    }
}