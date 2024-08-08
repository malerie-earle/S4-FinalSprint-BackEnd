package com.keyin.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.keyin.Users.UserDTO;
import com.keyin.Users.UserService;
import com.keyin.Users.User;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
            UserDTO user = userService.getUserByUsername(username);
            System.out.println(user);
            return ResponseEntity.ok(user);
    }

    @GetMapping("/users/id/{user_id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long user_id) {
        try {
            UserDTO user = userService.getUserById(user_id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    @PostMapping("/user")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO) {
        try {
            userService.saveUser(userDTO);
            return ResponseEntity.ok("User data saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user data: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String firstName,
            @RequestParam String lastName) {
        try {
            userService.signUp(username, password, email, firstName, lastName);
            return ResponseEntity.ok("User signed up successfully");
        } catch (CognitoIdentityProviderException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sign-up failed: " + e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestParam String username, @RequestParam String password) {
        try {
            AdminInitiateAuthResponse response = userService.signIn(username, password);
            return ResponseEntity.ok(response);
        } catch (CognitoIdentityProviderException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sign-in failed: " + e.getMessage());
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String username) {
        try {
            userService.resetPassword(username);
            return ResponseEntity.ok("Password reset successful");
        } catch (CognitoIdentityProviderException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password reset failed: " + e.getMessage());
        }
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncUsers() {
        try {
            userService.syncUsers();
            return ResponseEntity.ok("User synchronization completed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User synchronization failed: " + e.getMessage());
        }
    }
}
