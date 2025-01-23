package com.example.caloriesapp;

import com.example.caloriesapp.model.FoodEntry;
import com.example.caloriesapp.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorAndGetters() {
        // Përgatitja e të dhënave hyrëse
        String name = "John Doe";
        String email = "johndoe@example.com";
        String password = "password123";
        String role = "USER";
        LocalDate date = LocalDate.of(2025, 1, 1);

        // Krijimi i objektit User
        User user = new User(name, email, password, role);
        user.setDate(date);

        // Verifikimi i konstruktorit dhe getter-ave
        assertEquals(name, user.getName(), "Name nuk përputhet");
        assertEquals(email, user.getEmail(), "Email nuk përputhet");
        assertEquals(password, user.getPassword(), "Password nuk përputhet");
        assertEquals(role, user.getRole(), "Role nuk përputhet");
        assertEquals(date, user.getDate(), "Date nuk përputhet");
    }

    @Test
    void testSetters() {
        // Krijimi i objektit bosh
        User user = new User();

        // Vendosja e vlerave me setter-a
        String newName = "Jane Doe";
        String newEmail = "janedoe@example.com";
        String newPassword = "password456";
        String newRole = "ADMIN";
        LocalDate newDate = LocalDate.of(2025, 2, 1);

        user.setName(newName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        user.setRole(newRole);
        user.setDate(newDate);

        // Verifikimi i setter-ave
        assertEquals(newName, user.getName(), "Setter për name nuk funksionon siç duhet");
        assertEquals(newEmail, user.getEmail(), "Setter për email nuk funksionon siç duhet");
        assertEquals(newPassword, user.getPassword(), "Setter për password nuk funksionon siç duhet");
        assertEquals(newRole, user.getRole(), "Setter për role nuk funksionon siç duhet");
        assertEquals(newDate, user.getDate(), "Setter për date nuk funksionon siç duhet");
    }

    @Test
    void testFoodEntryManagement() {
        // Krijimi i objektit User dhe FoodEntry
        User user = new User("John Doe", "johndoe@example.com", "password123", "USER");
        FoodEntry foodEntry1 = new FoodEntry("Pizza", 300.0, 10.0, LocalDate.now().atStartOfDay(), user);
        FoodEntry foodEntry2 = new FoodEntry("Burger", 500.0, 15.0, LocalDate.now().atStartOfDay(), user);

        // Shtimi i FoodEntry
        user.addFoodEntry(foodEntry1);
        user.addFoodEntry(foodEntry2);

        // Verifikimi i shtimit
        List<FoodEntry> foodEntries = user.getFoodEntries();
        assertEquals(2, foodEntries.size(), "Numri i FoodEntry nuk përputhet");
        assertTrue(foodEntries.contains(foodEntry1), "FoodEntry1 nuk është shtuar");
        assertTrue(foodEntries.contains(foodEntry2), "FoodEntry2 nuk është shtuar");

        // Heqja e FoodEntry
        user.removeFoodEntry(foodEntry1);

        // Verifikimi i heqjes
        assertEquals(1, foodEntries.size(), "Numri i FoodEntry pas heqjes nuk përputhet");
        assertFalse(foodEntries.contains(foodEntry1), "FoodEntry1 nuk është hequr");
    }
}