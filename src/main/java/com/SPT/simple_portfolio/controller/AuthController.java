package com.SPT.simple_portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SPT.simple_portfolio.entity.User;
import com.SPT.simple_portfolio.service.UserService;
import jakarta.annotation.security.PermitAll;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
@PermitAll
public class AuthController {

    @Autowired
    private UserService userService;

    // Signup API: POST /api/signup
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(user.getEmail())){
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        userService.save(user);  // Save new user           
        return new ResponseEntity<>("User registered successfully. User ID: " + user.getId(), HttpStatus.CREATED);
    }

    // Login API: POST /api/login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        if (!userService.authenticate(user)) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        User existingUser = userService.getUserByUsername(user.getUsername());
        return new ResponseEntity<>("Login successful. User ID: " + existingUser.getId(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if not found
        }
    }

    
}