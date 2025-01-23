package com.example.caloriesapp.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    private String role; // Role: "USER" ose "ADMIN"
    private LocalDate date;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodEntry> foodEntries = new ArrayList<>();

    // Konstruktor pa parametra (i nevojshëm për JPA)
    public User(String testUser, String mail, String password) {
    }

    // Konstruktor me parametra
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {

    }


    // Getter dhe Setter për `id`
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter dhe Setter për `name`
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter dhe Setter për `email`
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter dhe Setter për `password`
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter dhe Setter për `role`
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Getter dhe Setter për `foodEntries`
    public List<FoodEntry> getFoodEntries() {
        return foodEntries;
    }

    public void setFoodEntries(List<FoodEntry> foodEntries) {
        this.foodEntries = foodEntries;
    }

    // Metoda për të shtuar një `FoodEntry` në listë
    public void addFoodEntry(FoodEntry foodEntry) {
        foodEntries.add(foodEntry);
        foodEntry.setUser(this);
    }

    // Metoda për të hequr një `FoodEntry` nga lista
    public void removeFoodEntry(FoodEntry foodEntry) {
        foodEntries.remove(foodEntry);
        foodEntry.setUser(null);
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public boolean isPresent() {
        return false;

    }

    public User orElseThrow(Object userNotFound) {
        return null;
    }
}
