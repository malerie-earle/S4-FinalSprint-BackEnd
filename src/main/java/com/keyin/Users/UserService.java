package com.keyin.Users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

            cognitoClient.adminCreateUser(request);
            saveUser(new UserDTO(username, email, firstName, lastName)); // userId is not used here

        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to sign up user: " + e.getMessage(), e);
        }
    }

    public void saveUser(UserDTO userDTO) {
        String sql = "INSERT INTO users (username, email, FName, LName) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE email = VALUES(email), FName = VALUES(FName), LName = VALUES(LName)";
        jdbcTemplate.update(sql,
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getFirstName(),
                userDTO.getLastName());
    }

    public AdminInitiateAuthResponse signIn(String username, String password) {
        try {
            AdminInitiateAuthRequest request = AdminInitiateAuthRequest.builder()
                    .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
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

    public UserDTO getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) ->
                new UserDTO(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("FName"),
                        rs.getString("LName")
                )
        );
    }

    public List<UserDTO> fetchUsers() {
        try {
            ListUsersRequest request = ListUsersRequest.builder()
                    .userPoolId(userPoolId)
                    .build();

            ListUsersResponse response = cognitoClient.listUsers(request);
            return response.users().stream().map(this::mapToUserDTO).collect(Collectors.toList());
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to fetch users: " + e.getMessage(), e);
        }
    }

    public UserDTO mapToUserDTO(UserType userType) {
        String username = userType.username();
        String email = userType.attributes().stream()
                .filter(attr -> "email".equals(attr.name()))
                .map(AttributeType::value)
                .findFirst()
                .orElse("Not Provided");
        String firstName = userType.attributes().stream()
                .filter(attr -> "given_name".equals(attr.name()))
                .map(AttributeType::value)
                .findFirst()
                .orElse("Not Provided");
        String lastName = userType.attributes().stream()
                .filter(attr -> "family_name".equals(attr.name()))
                .map(AttributeType::value)
                .findFirst()
                .orElse("Not Provided");

        return new UserDTO(username, email, firstName, lastName);
    }

    public void fetchAndPrintUserAttributes(String username) {
        try {
            AdminGetUserRequest request = AdminGetUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .build();

            AdminGetUserResponse response = cognitoClient.adminGetUser(request);

            System.out.println("Attributes for user: " + username);
            response.userAttributes().forEach(attr ->
                    System.out.println("Name: " + attr.name() + ", Value: " + attr.value()));

        } catch (CognitoIdentityProviderException e) {
            System.err.println("Failed to get user details for username " + username + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void syncUsers() {
        List<UserDTO> users = fetchUsers();
        updateOrInsertUsersInDatabase(users);
    }

    private void updateOrInsertUsersInDatabase(List<UserDTO> users) {
        String sql = "INSERT INTO users (username, email, FName, LName) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE email = VALUES(email), FName = VALUES(FName), LName = VALUES(LName)";

        for (UserDTO user : users) {
            jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
        }
    }

    public GetUserResponse getUserDetails(String accessToken) {
        try {
            GetUserRequest request = GetUserRequest.builder()
                    .accessToken(accessToken)
                    .build();

            return cognitoClient.getUser(request);
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to fetch user details: " + e.getMessage(), e);
        }
    }
}
