package com.adp.account_service.controller;

import com.adp.account_service.config.JwtUtil;
import com.adp.account_service.model.Account;
import com.adp.account_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password, @RequestParam boolean isAdmin) {
        System.out.println("Register request received with username: " + username);

        try {
            Account user = userService.registerUser(username, password, isAdmin);
            return ResponseEntity.ok("Request received: " + username);
        } catch (IllegalArgumentException e) {
            // Handle bad request due to invalid input
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected errors
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        System.out.println("Request Receieved");
        System.out.println("Login request received for username: " + username + password);
        try {
            Account user = userService.authenticateUser(username, password);
            if (user != null) {
                System.out.println("User authenticated successfully: " + username);

                // Fetch the isAdmin flag from the Account object
                boolean isAdmin = user.getAdmin();
                System.out.println("isAdmin: " + isAdmin);

                // Create the roles list based on the isAdmin status
                List<String> roles = new ArrayList<>();
                if (isAdmin) {
                    roles.add("ROLE_ADMIN");
                } else {
                    roles.add("ROLE_USER");
                }

                System.out.println("Roles list: " + roles);

                // Generate the token with roles
                String token = jwtUtil.generateToken(username, roles);
                return ResponseEntity.ok("Bearer " + token);
            } else {


                return ResponseEntity.badRequest().body("Invalid credentials");
            }
        } catch (Exception e) {
            System.out.println("Invalid credentials for username: " + username + password);
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Test successful";
    }
}
