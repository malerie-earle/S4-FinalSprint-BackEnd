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

    public User(String username, Map<String, String> attributes) {
    }

    public User(int user_id, String username, String email, String fname, String lname) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.FName = fname;
        this.LName = lname;
    }

    public User(String username, String email, String FName, String LName) {
        this.username = username;
        this.email = email;
        this.FName = FName;
        this.LName = LName;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String userId, String username, Map<String, String> attributes) {
        // This constructor seems to be incomplete. Ensure to handle attributes if needed.
    }

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
