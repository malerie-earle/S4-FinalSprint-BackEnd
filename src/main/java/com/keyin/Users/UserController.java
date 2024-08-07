package com.keyin.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GetUserResponse;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-details")
    public ResponseEntity<GetUserResponse> getUserDetails(@RequestHeader("Authorization") String authHeader) {
        try {
            String accessToken = authHeader.replace("Bearer ", "");
            LOGGER.info("Fetching details for access token: " + accessToken);
            GetUserResponse userDetails = userService.getUserDetails(accessToken);
            LOGGER.info("User details fetched successfully");
            return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            LOGGER.severe("Failed to fetch user details: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        try {
            UserDTO user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            LOGGER.severe("User not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO) {
        try {
            userService.saveUser(userDTO);
            return ResponseEntity.ok("User data saved successfully");
        } catch (Exception e) {
            LOGGER.severe("Error saving user data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user data: " + e.getMessage());
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String firstName, @RequestParam String lastName) {
        try {
            userService.signUp(username, password, email, firstName, lastName);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            LOGGER.severe("Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<AdminInitiateAuthResponse> signIn(@RequestParam String username, @RequestParam String password) {
        try {
            AdminInitiateAuthResponse response = userService.signIn(username, password);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.severe("Sign-in failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String username) {
        try {
            userService.resetPassword(username);
            return ResponseEntity.ok("Password reset successful");
        } catch (Exception e) {
            LOGGER.severe("Password reset failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password reset failed: " + e.getMessage());
        }
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncUsers() {
        try {
            userService.syncUsers();
            return ResponseEntity.ok("User synchronization completed successfully");
        } catch (Exception e) {
            LOGGER.severe("User synchronization failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User synchronization failed: " + e.getMessage());
        }
    }
}
