package com.adp.account_service.controller;

import com.adp.account_service.model.Account;
import com.adp.account_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password) {
        System.out.println("Register request received with username: " + username);

        try {
            Account user = userService.registerUser(username, password);
            return ResponseEntity.ok("Request received: " + username);
        } catch (IllegalArgumentException e) {
            // Handle bad request due to invalid input
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected errors
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Test successful";
    }
}
