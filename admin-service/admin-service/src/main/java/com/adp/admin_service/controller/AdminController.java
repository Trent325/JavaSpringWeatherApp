package com.adp.admin_service.controller;

import com.adp.admin_service.model.Account;
import com.adp.admin_service.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add-user")
    public ResponseEntity<Account> addUser(@RequestBody Account account) {
        System.out.println(account.getAdmin());
        Account newUser = userService.registerUser(account.getUsername(), account.getPassword(), account.getAdmin());
        System.out.println(newUser.toString());
        return ResponseEntity.ok(newUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-user")
    public ResponseEntity<Void> deleteUser(@RequestParam String username) {
        System.out.println("this is the username" + username);
        try {
            userService.deleteUserByUsername(username);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // HTTP 404 Not Found
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-user")
    public ResponseEntity<Void> updateUser(@RequestParam String username, @RequestBody Account updatedAccount) {
        System.out.println("Updating user: " + username);
        System.out.println("Updates: " + updatedAccount);
        try {
            userService.updateUserByUsername(username, updatedAccount);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // HTTP 404 Not Found
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> hi(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.ok("lets go");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
