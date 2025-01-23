package com.example.caloriesapp.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Lidhja me User

    // Konstruktor me parametra (për përdorim në aplikacion)
    public FoodEntry(String foodName, double calorieValue, double price, LocalDateTime entryDate, User user) {
        this.foodName = foodName;
        this.calorieValue = calorieValue;
        this.price = price;
        this.entryDate = entryDate;
        this.user = user;
    }

    public FoodEntry(String foodName, double calorieValue, double price, LocalDateTime now) {
    }

    @PrePersist
    public void onCreate() {
        if (this.entryDate == null) {
            this.entryDate = LocalDateTime.now();
        }
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        }
}