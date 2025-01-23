package com.example.caloriesapp;

import com.example.caloriesapp.model.FoodEntry;
import com.example.caloriesapp.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FoodEntryTest {

    @Test
    void testConstructorAndGetters() {
        // Përgatitja e të dhënave hyrëse
        String foodName = "Pizza";
        double calorieValue = 300.5;
        double price = 10.0;
        LocalDateTime entryDate = LocalDateTime.of(2025, 1, 23, 12, 0);
        User user = new User("John Doe", "johndoe@example.com", "password");

        // Krijimi i objektit FoodEntry
        FoodEntry foodEntry = new FoodEntry(foodName, calorieValue, price, entryDate, user);

        // Verifikimi i konstruktorit dhe getter-ave
        assertEquals(foodName, foodEntry.getFoodName(), "Food name nuk përputhet");
        assertEquals(calorieValue, foodEntry.getCalorieValue(), 0.01, "Calorie value nuk përputhet");
        assertEquals(price, foodEntry.getPrice(), 0.01, "Price nuk përputhet");
        assertEquals(entryDate, foodEntry.getEntryDate(), "Entry date nuk përputhet");
        assertEquals(user, foodEntry.getUser(), "User nuk përputhet");
    }

    @Test
    void testSetters() {
        // Krijimi i objektit bosh
        FoodEntry foodEntry = new FoodEntry();

        // Vendosja e vlerave me setter-a
        String newFoodName = "Burger";
        double newCalorieValue = 500.0;
        double newPrice = 15.0;
        LocalDateTime newEntryDate = LocalDateTime.of(2025, 1, 24, 18, 0);
        User newUser = new User("Jane Doe", "janedoe@example.com", "password123");

        foodEntry.setFoodName(newFoodName);
        foodEntry.setCalorieValue(newCalorieValue);
        foodEntry.setPrice(newPrice);
        foodEntry.setEntryDate(newEntryDate);
        foodEntry.setUser(newUser);

        // Verifikimi i setter-ave
        assertEquals(newFoodName, foodEntry.getFoodName(), "Setter për food name nuk funksionon siç duhet");
        assertEquals(newCalorieValue, foodEntry.getCalorieValue(), 0.01, "Setter për calorie value nuk funksionon siç duhet");
        assertEquals(newPrice, foodEntry.getPrice(), 0.01, "Setter për price nuk funksionon siç duhet");
        assertEquals(newEntryDate, foodEntry.getEntryDate(), "Setter për entry date nuk funksionon siç duhet");
        assertEquals(newUser, foodEntry.getUser(), "Setter për user nuk funksionon siç duhet");
    }

    @Test
    void testPrePersist() {
        // Krijimi i objektit pa vendosur entryDate
        FoodEntry foodEntry = new FoodEntry("Salad", 200.0, 8.0, null);

        // Thirrja e metodës onCreate
        foodEntry.onCreate();

        // Verifikimi që entryDate është vendosur në kohën aktuale
        assertNotNull(foodEntry.getEntryDate(), "Entry date duhet të jetë inicializuar");
        assertTrue(foodEntry.getEntryDate().isBefore(LocalDateTime.now().plusSeconds(1)),
                "Entry date duhet të jetë një kohë para tani");
    }
}