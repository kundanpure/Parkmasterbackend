package com.example.intellipark.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.example.intellipark.models.User;
import com.example.intellipark.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        // Encode the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        List<User> users = userRepository.findByUsername(username);
        if (users.size() > 1) {
            throw new RuntimeException("Multiple users found with username: " + username);
        }
        return users.isEmpty() ? null : users.get(0);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public boolean authenticateUser(String username, String plainPassword) {
        // Fetch user details from the database using username
        User user = findByUsername(username);

        if (user != null) {
            // Check if the plain password matches the encoded password stored in the database
            return matchesPassword(plainPassword, user.getPassword());
        }
        return false; // User not found or password mismatch
    }
}
