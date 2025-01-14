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
    public String registerUser(@RequestParam String name, @RequestParam String email, @RequestParam String password, Model model) {
        try {
            User user = userService.registerUser(name, email, password);  // Updated to use 'User' (uppercase)
            model.addAttribute("message", "User registered successfully!");
            return "login"; // Pas regjistrimit, mund të çonim në një faqe tjetër, si për shembull faqe për login
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "sign-up";
        }
    }
}
