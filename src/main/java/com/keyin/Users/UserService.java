package com.keyin.Users;

import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminResetUserPasswordRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.clientId}")
    private String clientId;

    public UserService(CognitoIdentityProviderClient cognitoClient) {
        this.cognitoClient = cognitoClient;
    }

    public void signUp(String username, String password, String email) {
        try {
            AdminCreateUserRequest request = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .temporaryPassword(password) // Use appropriate password handling
                    .userAttributes(AttributeType.builder().name("email").value(email).build())
                    .build();

            AdminCreateUserResponse response = cognitoClient.adminCreateUser(request);
            // Handle response if needed
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to sign up user: " + e.getMessage(), e);
        }
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
