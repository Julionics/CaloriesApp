// UserRepository.java
package com.example.caloriesapp.repository;

import com.example.caloriesapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // Custom method to find user by email
}
