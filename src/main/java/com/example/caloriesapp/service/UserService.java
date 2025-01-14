package com.example.caloriesapp.service;

import com.example.caloriesapp.model.User;  // Note: Use uppercase 'U' for class 'User'
import com.example.caloriesapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(String name, String email, String password) {
        // Check if email already exists in the database
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Create a new User object and set the fields
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);  // Save the password as plain text (no encryption)

        // Save the user to the database
        return userRepository.save(user);
    }
}
