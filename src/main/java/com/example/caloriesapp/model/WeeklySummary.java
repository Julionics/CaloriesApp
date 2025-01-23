package com.example.caloriesapp.model;
import java.util.List;


public class WeeklySummary {
    private List<DailyCalories> totalCaloriesPerDay; // Kaloritë ditore për javën
    private long daysThresholdExceeded;      // Numri i ditëve që kalojnë pragun
    private double totalExpenditure;         // Shpenzimet totale për javën

    // Konstruktori, getter, dhe setter
    public WeeklySummary(List<DailyCalories> totalCaloriesPerDay, long daysThresholdExceeded, double totalExpenditure) {
        this.totalCaloriesPerDay = totalCaloriesPerDay;
        this.daysThresholdExceeded = daysThresholdExceeded;
        this.totalExpenditure = totalExpenditure;
    }

    public WeeklySummary() {

    }

    public List<DailyCalories> getTotalCaloriesPerDay() {
        return totalCaloriesPerDay;
    }

    public void setTotalCaloriesPerDay(List<DailyCalories> totalCaloriesPerDay) {
        this.totalCaloriesPerDay = totalCaloriesPerDay;
    }

    public long getDaysThresholdExceeded() {
        return daysThresholdExceeded;
    }

    public void setDaysThresholdExceeded(long daysThresholdExceeded) {
        this.daysThresholdExceeded = daysThresholdExceeded;
    }

    public double getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(double totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }
}