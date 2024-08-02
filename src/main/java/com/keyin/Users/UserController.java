package com.keyin.Users;

import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        try {
            userService.signUp(username, password, email);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestParam String username, @RequestParam String password) {
        try {
            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                    .authFlow("ADMIN_NO_SRP_AUTH")
                    .userPoolId("your-user-pool-id") // Replace with your actual user pool ID
                    .clientId("your-client-id") // Replace with your actual client ID
                    .authParameters(Map.of(
                            "USERNAME", username,
                            "PASSWORD", password
                    ))
                    .build();

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
