package com.keyin.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class UserService {

    // Cognito Functions
    private final CognitoIdentityProviderClient cognitoClient;
    private final JdbcTemplate jdbcTemplate;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.clientId}")
    private String clientId;
    private int user_id;

    public UserService(CognitoIdentityProviderClient cognitoClient, JdbcTemplate jdbcTemplate) {
        this.cognitoClient = cognitoClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Fetch users from Cognito
    public List<UserDTO> fetchUsersFromCognito() {
        try {
            ListUsersRequest request = ListUsersRequest.builder()
                    .userPoolId(userPoolId)
                    .build();

            ListUsersResponse response = cognitoClient.listUsers(request);
            return response.users().stream().map(this::mapToUserDTO).collect(Collectors.toList());
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to fetch users from Cognito: " + e.getMessage(), e);
        }
    }

    // Map Cognito UserType to UserDTO
    private UserDTO mapToUserDTO(UserType userType) {
        String username = userType.username();
        String email = userType.attributes().stream()
                .filter(attr -> "email".equals(attr.name()))
                .map(attr -> attr.value())
                .findFirst()
                .orElse("Not Provided");
        String firstName = userType.attributes().stream()
                .filter(attr -> "given_name".equals(attr.name()))
                .map(attr -> attr.value())
                .findFirst()
                .orElse("Not Provided");
        String lastName = userType.attributes().stream()
                .filter(attr -> "family_name".equals(attr.name()))
                .map(attr -> attr.value())
                .findFirst()
                .orElse("Not Provided");

        return new UserDTO(username, email, firstName, lastName);
    }

    public void signUp(String username, String password, String email, String firstName, String lastName) {
        try {
            System.out.println("Signing up user with attributes:");
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);

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

            // Fetch user from Cognito
            UserDTO userDTO = fetchUserFromCognitoByUsername(username);

            // Save user to database
            saveUser(userDTO);

        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to sign up user: " + e.getMessage(), e);
        }
    }

    private UserDTO fetchUserFromCognitoByUsername(String username) {
        try {
            AdminGetUserRequest request = AdminGetUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .build();

            AdminGetUserResponse response = cognitoClient.adminGetUser(request);

            // Map Cognito response to UserDTO
            return new UserDTO(
                    response.username(),
                    response.userAttributes().stream()
                            .filter(attr -> "email".equals(attr.name()))
                            .map(AttributeType::value)
                            .findFirst()
                            .orElse("Not Provided"),
                    response.userAttributes().stream()
                            .filter(attr -> "given_name".equals(attr.name()))
                            .map(AttributeType::value)
                            .findFirst()
                            .orElse("Not Provided"),
                    response.userAttributes().stream()
                            .filter(attr -> "family_name".equals(attr.name()))
                            .map(AttributeType::value)
                            .findFirst()
                            .orElse("Not Provided")
            );
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to fetch user from Cognito: " + e.getMessage(), e);
        }
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
        List<UserDTO> users = fetchUsersFromCognito();
        for (UserDTO user : users) {
            saveUser(user);
        }
    }


    // Database Functions
    @Autowired
    private UserRepository userRepository;

    // Fetch users from database
    public List<UserDTO> fetchUsersFromDatabase() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new UserDTORowMapper());
    }

    // Get all users from database
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    // Fetch user by username from the database
    public UserDTO getUserByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{username},
                new UserDTORowMapper()
        );
    }

    public UserDTO getUserDetails() {
        // Get the currently authenticated user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Fetch user details from the database using the username
        return getUserByUsername(username); // Ensure this returns UserDTO
    }

    public void saveUser(UserDTO userDTO) {
        String sql = "INSERT INTO user (username, email, FName, LName) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE email = VALUES(email), FName = VALUES(FName), LName = VALUES(LName)";
        jdbcTemplate.update(sql,
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getFirstName(),
                userDTO.getLastName());
    }

    public UserDTO getUserById(long user_id) {
        return userRepository.findById(user_id)
                .map(user -> new UserDTO(user.getUsername(), user.getEmail(), user.getFName(), user.getLName()))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // RowMapper implementation to map ResultSet to UserDTO
    private static class UserDTORowMapper implements RowMapper<UserDTO> {
        @Override
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserDTO(
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("FName"),
                    rs.getString("LName")
            );
        }
    }
}
