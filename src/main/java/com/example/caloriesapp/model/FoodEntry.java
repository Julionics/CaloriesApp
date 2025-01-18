package com.example.caloriesapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FoodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;
    private double calorieValue;
    private double price;

    @Column(name = "entry_date", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime entryDate;

    // Konstruktor pa parametra (kërkohet nga Hibernate)
    public FoodEntry(String foodName, double calorieValue, LocalDateTime now, double price) {
    }

    // Konstruktor me parametra (për përdorim në aplikacion)
    public FoodEntry(String foodName, double calorieValue, double price, LocalDateTime entryDate) {
        this.foodName = foodName;
        this.calorieValue = calorieValue;
        this.price = price;
        this.entryDate = entryDate;
    }

    public FoodEntry() {

    }

    // Getters dhe Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getCalorieValue() {
        return calorieValue;
    }

    public void setCalorieValue(double calorieValue) {
        this.calorieValue = calorieValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }
}