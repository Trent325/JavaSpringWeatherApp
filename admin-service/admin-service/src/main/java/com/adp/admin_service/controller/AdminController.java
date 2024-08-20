package com.adp.admin_service.controller;

import com.adp.admin_service.model.Account;
import com.adp.admin_service.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody Account account) {
        Account newUser = userService.registerUser(account.getUsername(), account.getPassword());
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/test")
    public ResponseEntity<String> hi(){
        return ResponseEntity.ok("lets go");
    }
}
