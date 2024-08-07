package com.keyin.Users;

import jakarta.persistence.*;
import java.util.Map;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String username;
    private String email;
    private String FName;
    private String LName;

    // Default constructor for JPA
    public User() {
    }

    // Constructor with all fields
    public User(int user_id, String username, String email, String FName, String LName) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.FName = FName;
        this.LName = LName;
    }

    // Constructor without user_id (useful for creating new users before saving)
    public User(String username, String email, String FName, String LName) {
        this.username = username;
        this.email = email;
        this.FName = FName;
        this.LName = LName;
    }

    // Constructor with attributes map
    public User(String username, Map<String, String> attributes) {
        this.username = username;
        this.email = attributes.get("email");
        this.FName = attributes.get("FName");
        this.LName = attributes.get("LName");
    }

    // Getters and Setters
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }
}
