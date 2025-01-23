package com.example.caloriesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.caloriesapp.repository") // Kontrollon për `Repositories`
public class CaloriesAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaloriesAppApplication.class, args);
    }
}
