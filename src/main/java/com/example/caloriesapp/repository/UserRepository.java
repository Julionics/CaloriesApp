package com.example.caloriesapp.repository;

import com.example.caloriesapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u JOIN FoodEntry fe ON u.id = fe.user.id " +
            "WHERE fe.entryDate BETWEEN :start AND :end " +
            "GROUP BY u.id " +
            "HAVING SUM(fe.price) > :limit")
    List<User> findUsersExceedingMonthlyLimit(@Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end,
                                              @Param("limit") double limit);
}

