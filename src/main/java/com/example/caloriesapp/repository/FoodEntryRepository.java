package com.example.caloriesapp.repository;

import com.example.caloriesapp.model.DailyCalories;
import com.example.caloriesapp.model.FoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FoodEntryRepository extends JpaRepository<FoodEntry, Long> {
    long countByEntryDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT AVG(fe.calorieValue) FROM FoodEntry fe WHERE fe.entryDate BETWEEN :start AND :end")
    double calculateAverageCaloriesForDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // Merr ditët që tejkalojnë pragun e kalorive
    @Query("SELECT DATE(fe.entryDate) FROM FoodEntry fe " +
            "GROUP BY DATE(fe.entryDate) " +
            "HAVING SUM(fe.calorieValue) > :threshold")
    List<java.sql.Date> findDaysExceedingCalorieThreshold(@Param("threshold") double dailyCalorieThreshold);

    // Merr hyrjet ushqimore për një datë të caktuar
    @Query("SELECT fe FROM FoodEntry fe WHERE DATE(fe.entryDate) = :date")
    List<FoodEntry> findByEntryDate(@Param("date") LocalDate date);

    // Merr hyrjet ushqimore për një interval datash
    @Query("SELECT fe FROM FoodEntry fe WHERE DATE(fe.entryDate) BETWEEN :startDate AND :endDate")
    List<FoodEntry> findByEntryDateBetween(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    // Llogarit totalin e shpenzimeve për një muaj të caktuar
    @Query("SELECT SUM(fe.price) FROM FoodEntry fe " +
            "WHERE YEAR(fe.entryDate) = :year AND MONTH(fe.entryDate) = :month")
    Double getTotalExpenditureForMonth(@Param("year") int year,
                                       @Param("month") int month);

    // Merr totalin e kalorive për çdo ditë të një jave
    @Query("SELECT new com.example.caloriesapp.model.DailyCalories(DATE(fe.entryDate), SUM(fe.calorieValue)) " +
            "FROM FoodEntry fe " +
            "WHERE DATE(fe.entryDate) BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(fe.entryDate)")
    List<DailyCalories> getTotalCaloriesPerDayForWeek(@Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);

    // Llogarit totalin e shpenzimeve për një interval datash
    @Query("SELECT SUM(fe.price) " +
            "FROM FoodEntry fe " +
            "WHERE DATE(fe.entryDate) BETWEEN :startDate AND :endDate")
    Double getTotalExpenditureForWeek(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);


    // Merr numrin e hyrjeve të shtuar në 7 ditët e fundit krahasuar me javën paraardhëse
    @Query("SELECT COUNT(fe) FROM FoodEntry fe WHERE fe.entryDate BETWEEN :startDate AND :endDate")
    long countEntriesByDateRange(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);

    // Mesatarja e kalorive për përdorues për 7 ditët e fundit
    @Query("SELECT AVG(fe.calorieValue) FROM FoodEntry fe WHERE fe.entryDate BETWEEN :startDate AND :endDate")
    Double findAverageCaloriesByDateRange(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    // Merr listën e përdoruesve që tejkalojnë limitin mujor të shpenzimeve
    @Query("SELECT fe.user.name FROM FoodEntry fe " +
            "WHERE YEAR(fe.entryDate) = :year AND MONTH(fe.entryDate) = :month " +
            "GROUP BY fe.user.id " +
            "HAVING SUM(fe.price) > :limit")
    List<String> findUsersExceededMonthlyLimit(@Param("year") int year,
                                               @Param("month") int month,
                                               @Param("limit") double limit);


}