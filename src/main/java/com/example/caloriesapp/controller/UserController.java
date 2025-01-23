package com.example.caloriesapp.controller;

import com.example.caloriesapp.model.User;  // Corrected to use 'User' (uppercase 'U')
import com.example.caloriesapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint për të shfaqur formën e regjistrimit
    @GetMapping("/signup")
    public String showSignUpForm() {
        return "sign-up";  // Ky është emri i skedarit HTML (sign-up.html)
    }

    // Endpoint për të trajtuar formën e dërguar (POST request)
    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        try {
            // Krijo një përdorues të ri
            User user = userService.registerUser(name, email, password);

            // Ridrejto në faqen kryesore të FoodEntry pas regjistrimit të suksesshëm
            return "redirect:/food-entry/new";
        } catch (Exception e) {
            // Nëse ka një gabim, rikthehu te forma e regjistrimit me një mesazh gabimi
            model.addAttribute("error", e.getMessage());
            return "sign-up";
        }
    }

}
