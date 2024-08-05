package com.keyin.Users;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "user_sequence")
    private int user_id;
    private String username;
    private String email;
    private String FName;
    private String LName;

    public User(int user_id, String username, String email, String fname, String lname) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.FName = fname;
        this.LName = lname;
    }

    public User(String username, String email, String fname, String lname) {
        this.username = username;
        this.email = email;
        this.FName = fname;
        this.LName = lname;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
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

    public String getFname() {
        return FName;
    }

    public void setFname(String fname) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }
}
