package com.example.caloriesapp.repository;



import com.example.caloriesapp.model.foodEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodEntryRepository extends JpaRepository<foodEntry, Long> {
}
