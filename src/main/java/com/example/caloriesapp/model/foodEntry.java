package com.example.caloriesapp.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
public class foodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;
    private double calorieValue;
    private LocalDateTime dateTime;

    private LocalDate date;

    public foodEntry(String foodName, double calorieValue, LocalDateTime now) {
    }

    public foodEntry(String foodName, double calorieValue, LocalDateTime dateTime , LocalDate date) {
        this.foodName = foodName;
        this.calorieValue = calorieValue;
        this.dateTime = dateTime;
        this.date = date;
    }

    // Getter dhe Setter
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
