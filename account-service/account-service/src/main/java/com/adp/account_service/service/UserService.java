package com.adp.account_service.service;

import com.adp.account_service.model.Account;
import com.adp.account_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.ArrayList;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Account registerUser(String username, String password, boolean isAdmin) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        Account user = new Account();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAdmin(isAdmin);// Encrypt password
        return userRepository.save(user);
    }

    public Account authenticateUser(String username, String password) {
        Account user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = userRepository.findByUsername(username);
        System.out.println("User found with username: " + username);
        if (user == null) {
            System.out.println("User not found with username: " + username);

            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
