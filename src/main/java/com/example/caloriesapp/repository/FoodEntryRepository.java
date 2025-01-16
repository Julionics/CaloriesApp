package com.example.caloriesapp.repository;

import com.example.caloriesapp.model.foodEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FoodEntryRepository extends JpaRepository<foodEntry, Long> {

    // Query për të marrë totalin e kalorive për një datë të caktuar
    @Query("SELECT SUM(f.calorieValue) FROM foodEntry f WHERE f.date = :date")
    Double getTotalCaloriesByDate(LocalDate date);

    // Query për të marrë të gjitha hyrjet sipas vitit dhe muajit
    @Query("SELECT f FROM foodEntry f WHERE YEAR(f.date) = :year AND MONTH(f.date) = :month")
    List<foodEntry> findByYearAndMonth(int year, int month);

    // Query për të llogaritur totalin e shpenzimeve mujore
    @Query("SELECT SUM(f.price) FROM foodEntry f WHERE YEAR(f.date) = :year AND MONTH(f.date) = :month")
    Double getTotalExpenditureByYearAndMonth(int year, int month);

    // Query për të marrë totalin e kalorive për një javë, grupuar sipas ditës
    @Query("SELECT SUM(f.calorieValue) FROM foodEntry f WHERE f.date BETWEEN :startDate AND :endDate GROUP BY f.date")
    List<Double> getTotalCaloriesPerDayForWeek(LocalDate startDate, LocalDate endDate);

    // Query për të marrë numrin e ditëve që kaloritë tejkaluan pragun për një javë
    @Query("SELECT COUNT(DISTINCT f.date) FROM foodEntry f WHERE f.date BETWEEN :startDate AND :endDate AND f.calorieValue > 2500")
    Long getDaysCalorieThresholdExceeded(LocalDate startDate, LocalDate endDate);

    // Query për të marrë totalin e shpenzimeve për një javë
    @Query("SELECT SUM(f.price) FROM foodEntry f WHERE f.date BETWEEN :startDate AND :endDate")
    Double getTotalExpenditureForWeek(LocalDate startDate, LocalDate endDate);

    // Query për të marrë regjistrimet sipas një intervali datash
    @Query("SELECT f FROM foodEntry f WHERE f.date BETWEEN :startDate AND :endDate")
    List<foodEntry> findByDateRange(LocalDate startDate, LocalDate endDate);

}

