package com.example.caloriesapp.model;

import java.util.List;

public class WeeklySummary {

    private List<Double> totalCaloriesPerDay;
    private Long daysThresholdExceeded;
    private Double totalExpenditure;

    // Konstruktor
    public WeeklySummary(List<Double> totalCaloriesPerDay, Long daysThresholdExceeded, Double totalExpenditure) {
        this.totalCaloriesPerDay = totalCaloriesPerDay;
        this.daysThresholdExceeded = daysThresholdExceeded;
        this.totalExpenditure = totalExpenditure;
    }

    // Getters dhe Setters
    public List<Double> getTotalCaloriesPerDay() {
        return totalCaloriesPerDay;
    }

    public void setTotalCaloriesPerDay(List<Double> totalCaloriesPerDay) {
        this.totalCaloriesPerDay = totalCaloriesPerDay;
    }

    public Long getDaysThresholdExceeded() {
        return daysThresholdExceeded;
    }

    public void setDaysThresholdExceeded(Long daysThresholdExceeded) {
        this.daysThresholdExceeded = daysThresholdExceeded;
    }

    public Double getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(Double totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }
}
