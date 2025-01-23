package com.example.caloriesapp;

import com.example.caloriesapp.model.DailyCalories;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyCaloriesTest {

    @Test
    void testConstructorAndGetters() {
        // Përgatitja e të dhënave hyrëse
        LocalDate localDate = LocalDate.of(2025, 1, 23);
        Date sqlDate = Date.valueOf(localDate);
        double totalCalories = 2500.0;

        // Krijimi i objektit DailyCalories
        DailyCalories dailyCalories = new DailyCalories(sqlDate, totalCalories);

        // Verifikimi i konstruktorit dhe getter-ave
        assertEquals(localDate, dailyCalories.getDate(), "Data nuk përputhet");
        assertEquals(totalCalories, dailyCalories.getTotalCalories(), 0.01, "Kaloritë totale nuk përputhen");
    }

    @Test
    void testSetters() {
        // Krijimi i objektit bosh
        DailyCalories dailyCalories = new DailyCalories(Date.valueOf(LocalDate.now()), 0.0);

        // Vendosja e vlerave me setter-a
        LocalDate newDate = LocalDate.of(2025, 2, 1);
        double newCalories = 3000.0;

        dailyCalories.setDate(newDate);
        dailyCalories.setTotalCalories(newCalories);

        // Verifikimi i setter-ave
        assertEquals(newDate, dailyCalories.getDate(), "Setter për datën nuk funksionon siç duhet");
        assertEquals(newCalories, dailyCalories.getTotalCalories(), 0.01, "Setter për kaloritë totale nuk funksionon siç duhet");
    }
}