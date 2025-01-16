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

    private double price;

    // Konstruktorët
    public foodEntry(String foodName, double calorieValue, LocalDateTime now) {
        // Konstruktor bosh ose specifik për raste të tjera
    }

    public foodEntry(String foodName, double calorieValue, LocalDateTime dateTime, LocalDate date, double price) {
        this.foodName = foodName;
        this.calorieValue = calorieValue;
        this.dateTime = dateTime;
        this.date = date;
        this.price = price;
    }

    public foodEntry(String foodName, double calorieValue, LocalDateTime now, double price) {
    }

    public foodEntry() {

    }

    // Getter dhe Setter për atributin 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter dhe Setter për atributin 'foodName'
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    // Getter dhe Setter për atributin 'calorieValue'
    public double getCalorieValue() {
        return calorieValue;
    }

    public void setCalorieValue(double calorieValue) {
        this.calorieValue = calorieValue;
    }

    // Getter dhe Setter për atributin 'dateTime'
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // Getter dhe Setter për atributin 'date'
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Getter dhe Setter për atributin 'price'
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
