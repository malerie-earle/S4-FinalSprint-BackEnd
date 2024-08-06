package com.keyin.Users;

import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UserType;
import software.amazon.awssdk.core.exception.SdkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class CognitoUserFetch {

    private final CognitoIdentityProviderClient cognitoClient;
    private final JdbcTemplate jdbcTemplate;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    public CognitoUserFetch(CognitoIdentityProviderClient cognitoClient, JdbcTemplate jdbcTemplate) {
        this.cognitoClient = cognitoClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void fetchAndStoreUsers() {
        try {
            ListUsersRequest request = ListUsersRequest.builder()
                    .userPoolId(userPoolId)
                    .build();

            ListUsersResponse response = cognitoClient.listUsers(request);

            for (UserType user : response.users()) {
                String username = user.username();
                String email = user.attributes().stream()
                        .filter(attr -> "email".equals(attr.name()))
                        .map(attr -> attr.value())
                        .findFirst()
                        .orElse(null);
                String firstName = user.attributes().stream()
                        .filter(attr -> "given_name".equals(attr.name()))
                        .map(attr -> attr.value())
                        .findFirst()
                        .orElse(null);
                String lastName = user.attributes().stream()
                        .filter(attr -> "family_name".equals(attr.name()))
                        .map(attr -> attr.value())
                        .findFirst()
                        .orElse(null);

                saveUserToDatabase(username, email, firstName, lastName);
            }
        } catch (SdkException e) {
            System.err.println("Error fetching users from Cognito: " + e.getMessage());
        } finally {
            cognitoClient.close();
        }
    }

    private void saveUserToDatabase(String username, String email, String firstName, String lastName) {
        String sql = "INSERT INTO users (username, email, FName, LName) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, username, email, firstName, lastName);
    }
}
