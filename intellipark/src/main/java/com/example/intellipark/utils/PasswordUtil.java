package com.example.intellipark.utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Method to encode a password
    public static String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // Method to check if the raw password matches the encoded password
    public static boolean checkPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
