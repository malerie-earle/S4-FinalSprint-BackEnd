package com.keyin.Users;

import com.keyin.Users.User;

public class UserDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;


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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String FName) {
        this.firstName = FName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String LName) {
        this.lastName = LName;
    }
}


