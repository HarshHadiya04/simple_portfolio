package com.SPT.simple_portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SPT.simple_portfolio.entity.User;
import com.SPT.simple_portfolio.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean authenticate(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null && user.getEmail().equals(existingUser.getEmail())) {
            return passwordEncoder.matches(user.getPassword(), existingUser.getPassword());
        }
        return false;
    }

    public User getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
