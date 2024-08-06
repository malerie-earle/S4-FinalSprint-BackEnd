package com.keyin.Users;

import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminResetUserPasswordRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final CognitoIdentityProviderClient cognitoClient;
    private final JdbcTemplate jdbcTemplate;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.clientId}")
    private String clientId;

    public UserService(CognitoIdentityProviderClient cognitoClient, JdbcTemplate jdbcTemplate) {
        this.cognitoClient = cognitoClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void signUp(String username, String password, String email, String firstName, String lastName) {
        try {
            AdminCreateUserRequest request = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .temporaryPassword(password)
                    .userAttributes(
                            AttributeType.builder().name("email").value(email).build(),
                            AttributeType.builder().name("given_name").value(firstName).build(),
                            AttributeType.builder().name("family_name").value(lastName).build()
                    )
                    .build();

            AdminCreateUserResponse response = cognitoClient.adminCreateUser(request);

            // Save user to database
            saveUserToDatabase(username, email, firstName, lastName);

        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to sign up user: " + e.getMessage(), e);
        }
    }

    private void saveUserToDatabase(String username, String email, String firstName, String lastName) {
        String sql = "INSERT INTO users (username, email, FName, LName) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, username, email, firstName, lastName);
    }

    public AdminInitiateAuthResponse signIn(String username, String password) {
        try {
            AdminInitiateAuthRequest request = AdminInitiateAuthRequest.builder()
                    .authFlow("ADMIN_NO_SRP_AUTH")
                    .clientId(clientId)
                    .userPoolId(userPoolId)
                    .authParameters(Map.of(
                            "USERNAME", username,
                            "PASSWORD", password
                    ))
                    .build();

            return cognitoClient.adminInitiateAuth(request);
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to sign in user: " + e.getMessage(), e);
        }
    }

    public void resetPassword(String username) {
        try {
            AdminResetUserPasswordRequest request = AdminResetUserPasswordRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .build();

            cognitoClient.adminResetUserPassword(request);
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to reset password: " + e.getMessage(), e);
        }
    }
}
