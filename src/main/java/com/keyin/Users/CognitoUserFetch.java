package com.keyin.Users;

import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UserType;
import software.amazon.awssdk.core.exception.SdkException;

public class CognitoUserFetch {

    private final CognitoIdentityProviderClient cognitoClient;

    public CognitoUserFetch() {
        cognitoClient = CognitoIdentityProviderClient.create();
    }

    public void fetchAndStoreUsers() {
        try {
            ListUsersRequest request = ListUsersRequest.builder()
                    .userPoolId("us-east-1_MBx8BsTnk")
                    .build();

            ListUsersResponse response = cognitoClient.listUsers(request);

            for (UserType user : response.users()) {
                // Extract user attributes
                String username = user.username();
                String email = user.attributes().stream()
                        .filter(attr -> "email".equals(attr.name()))
                        .map(attr -> attr.value())
                        .findFirst()
                        .orElse(null);

                // Save to your database
                // You will need a repository or service to handle this
                // userRepository.save(new User(username, email));
            }
        } catch (SdkException e) {
            // Handle SDK-specific exceptions
            System.err.println("Error fetching users from Cognito: " + e.getMessage());
        } finally {
            cognitoClient.close(); // Ensure client is closed
        }
    }
}
