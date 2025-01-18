package com.example.caloriesapp.model;

import java.time.LocalDate;

public class DailyCalories {
    private LocalDate date;
    private double totalCalories;

    // Konstruktor i kërkuar nga HQL
    public DailyCalories(java.sql.Date date, double totalCalories) {
        this.date = date.toLocalDate(); // Konvertim nga java.sql.Date në LocalDate
        this.totalCalories = totalCalories;
    }

    // Getters dhe Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }
}