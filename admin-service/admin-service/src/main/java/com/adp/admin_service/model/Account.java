package com.adp.admin_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String username;
    private String password;
//    @JsonProperty("isAdmin")
    private boolean is_admin;

    public Account() {}

    // Constructor with fields
    public Account(String username, String password, Boolean is_admin) {
        this.username = username;
        this.password = password;
        this.is_admin = is_admin;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return this.is_admin;
    }

    public void setAdmin(boolean admin) {
        this.is_admin = admin;
    }

    @Override
    public String toString(){
        return "" + this.username + " " + this.password + " "+this.is_admin;
    }
}
