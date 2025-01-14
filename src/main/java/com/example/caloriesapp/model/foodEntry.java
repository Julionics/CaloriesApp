package com.example.caloriesapp.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class foodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;
    private double calorieValue;
    private LocalDateTime dateTime;

    public foodEntry() {
    }

    public foodEntry(String foodName, double calorieValue, LocalDateTime dateTime) {
        this.foodName = foodName;
        this.calorieValue = calorieValue;
        this.dateTime = dateTime;
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
}
