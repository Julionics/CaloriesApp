package com.example.caloriesapp.controller;

import com.example.caloriesapp.service.CalorieThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class CalorieThresholdController {

    @Autowired
    private CalorieThresholdService calorieThresholdService;

    @GetMapping("/check-threshold")
    public String checkCalorieThreshold(Model model) {
        LocalDate today = LocalDate.now();
        boolean exceeded = calorieThresholdService.isThresholdExceeded(today);

        if (exceeded) {
            model.addAttribute("warning", "Kujdes! Keni tejkaluar limitin ditor të kalorive prej 2,500.");
        } else {
            model.addAttribute("message", "Ju jeni brenda limitit ditor të kalorive.");
        }

        return "calorieThreshold";
    }
}
