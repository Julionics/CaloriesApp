package com.example.caloriesapp.service;

import com.example.caloriesapp.model.User;
import com.example.caloriesapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Për të koduar fjalëkalimet

    public User registerUser(String name, String email, String password, String role) {
        // Validimi për fushat bosh ose null
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("Name cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("Password cannot be null or empty");
        }

        // Kontrollo nëse email-i ekziston në bazën e të dhënave
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Krijo një objekt të ri të përdoruesit
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Kodimi i fjalëkalimit
        user.setRole(role); // Vendos rolin (p.sh., "ROLE_USER" ose "ROLE_ADMIN")

        // Ruaj përdoruesin në bazën e të dhënave
        return userRepository.save(user);
    }


    public User registerUser(String name, String email, String password) {
        return null;
    }
}
