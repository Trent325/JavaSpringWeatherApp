package com.adp.admin_service.service;

import com.adp.admin_service.model.Account;
import com.adp.admin_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        String role = account.getAdmin() ? "ADMIN" : "USER";
        return org.springframework.security.core.userdetails.User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles(role)
                .build();
    }

    public Account registerUser(String username, String password, Boolean isAdmin) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        Account user = new Account(username, passwordEncoder.encode(password), isAdmin);

        return userRepository.save(user);
    }

    public void deleteUserByUsername(String username) {
        Account user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
        userRepository.delete(user);
    }

    public void updateUserByUsername(String username, Account updatedAccount) {
        Account existingAccount = userRepository.findByUsername(username);
        if (existingAccount == null) {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
        existingAccount.setUsername(updatedAccount.getUsername());
        existingAccount.setPassword(passwordEncoder.encode(updatedAccount.getPassword()));
        existingAccount.setAdmin(updatedAccount.getAdmin());

        userRepository.save(existingAccount);
    }

    public Account findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Account> getAllUsers() {
        return userRepository.findAll(); // Assumes userRepository extends JpaRepository<Account, Long>
    }
}
