package com.keyin.Users;

import java.util.HashMap;
import java.util.Map;

public class UserDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    public UserDTO() {
    }

    public UserDTO(String username, String email, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Map<String, String> getAttributes() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("username", username);
        attributes.put("email", email);
        attributes.put("firstName", firstName);
        attributes.put("lastName", lastName);
        return attributes;
    }
}
