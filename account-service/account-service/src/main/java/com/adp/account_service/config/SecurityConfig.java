package com.adp.account_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/register", "/api/test", "/api/login").permitAll() // Allow access to register and test
                        .anyRequest().authenticated() // Require authentication for other requests
                )
                .formLogin(formLogin -> formLogin.permitAll()) // Enable form login
                .logout(logout -> logout.permitAll()); // Enable logout

        return http.build();
    }
}
