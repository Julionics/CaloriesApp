package com.example.caloriesapp.repository;



import com.example.caloriesapp.model.foodEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface FoodEntryRepository extends JpaRepository<foodEntry, Long> {


    @Query("SELECT SUM(f.calorieValue) FROM foodEntry f WHERE f.date = :date")
    Double getTotalCaloriesByDate(LocalDate date);
}
