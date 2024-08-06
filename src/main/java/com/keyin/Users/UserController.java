package com.keyin.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;

@RestController
@RequestMapping("/api/auth")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        try {
            String sql = "SELECT username, email, FName AS firstName, LName AS lastName FROM users WHERE username = ?";
            UserDTO user = jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper<>(UserDTO.class));
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO) {
        try {
            String sql = "INSERT INTO users (username, email, FName, LName) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, userDTO.getUsername(), userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName());
            return ResponseEntity.ok("User data saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user data: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String firstName, @RequestParam String lastName) {
        try {
            userService.signUp(username, password, email, firstName, lastName);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestParam String username, @RequestParam String password) {
        try {
            AdminInitiateAuthResponse authResponse = userService.signIn(username, password);
            String idToken = authResponse.authenticationResult().idToken();
            String accessToken = authResponse.authenticationResult().accessToken();
            String refreshToken = authResponse.authenticationResult().refreshToken();

            return ResponseEntity.ok(new AuthResponse(idToken, accessToken, refreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sign in failed: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String username) {
        try {
            userService.resetPassword(username);
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password reset failed: " + e.getMessage());
        }
    }

    // Inner class to structure the authentication response
    private static class AuthResponse {
        private String idToken;
        private String accessToken;
        private String refreshToken;

        public AuthResponse(String idToken, String accessToken, String refreshToken) {
            this.idToken = idToken;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getIdToken() {
            return idToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
