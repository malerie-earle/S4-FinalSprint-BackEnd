package com.keyin.Users;

import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminResetUserPasswordResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private CognitoIdentityProviderClient cognitoClient;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignUpSuccess() {
        AdminCreateUserResponse response = AdminCreateUserResponse.builder().build();
        when(cognitoClient.adminCreateUser(any(AdminCreateUserRequest.class))).thenReturn(response);

        userService.signUp("testUser", "testPass", "test@example.com", "testFirstName", "testLastName");

        verify(jdbcTemplate).update(anyString(), eq("testUser"), eq("test@example.com"), eq("testFirstName"), eq("testLastName"));
    }

    @Test
    void testSignUpFailure() {
        when(cognitoClient.adminCreateUser(any(AdminCreateUserRequest.class)))
                .thenThrow(CognitoIdentityProviderException.builder().message("Error").build());

        assertThrows(RuntimeException.class, () -> {
            userService.signUp("testUser", "testPass", "test@example.com", "testFirstName", "testLastName");
        });
    }

    @Test
    void testSignInSuccess() {
        AuthenticationResultType authResult = AuthenticationResultType.builder()
                .idToken("idToken")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
        AdminInitiateAuthResponse response = AdminInitiateAuthResponse.builder()
                .authenticationResult(authResult)
                .build();
        when(cognitoClient.adminInitiateAuth(any(AdminInitiateAuthRequest.class))).thenReturn(response);

        AdminInitiateAuthResponse authResponse = userService.signIn("testUser", "testPass");

        assertNotNull(authResponse.authenticationResult().idToken());
        assertNotNull(authResponse.authenticationResult().accessToken());
        assertNotNull(authResponse.authenticationResult().refreshToken());
    }


    @Test
    void testSignInFailure() {
        when(cognitoClient.adminInitiateAuth(any(AdminInitiateAuthRequest.class)))
                .thenThrow(CognitoIdentityProviderException.builder().message("Error").build());

        assertThrows(RuntimeException.class, () -> {
            userService.signIn("testUser", "testPass");
        });
    }

    @Test
    void testResetPasswordSuccess() {
        AdminResetUserPasswordResponse response = AdminResetUserPasswordResponse.builder().build();
        when(cognitoClient.adminResetUserPassword(any(AdminResetUserPasswordRequest.class))).thenReturn(response);

        userService.resetPassword("testUser");
    }

    @Test
    void testResetPasswordFailure() {
        when(cognitoClient.adminResetUserPassword(any(AdminResetUserPasswordRequest.class)))
                .thenThrow(CognitoIdentityProviderException.builder().message("Error").build());

        assertThrows(RuntimeException.class, () -> {
            userService.resetPassword("testUser");
        });
    }


    @Test
    void testMapToUserDTO() throws Exception {
        UserType userType = UserType.builder()
                .username("testuser")
                .attributes(
                        AttributeType.builder().name("email").value("test@example.com").build(),
                        AttributeType.builder().name("given_name").value("Test").build(),
                        AttributeType.builder().name("family_name").value("User").build()
                )
                .build();

        Method method = UserService.class.getDeclaredMethod("mapToUserDTO", UserType.class);
        method.setAccessible(true);
        UserDTO userDTO = (UserDTO) method.invoke(userService, userType);

        assertEquals("testuser", userDTO.getUsername());
        assertEquals("test@example.com", userDTO.getEmail());
        assertEquals("Test", userDTO.getFirstName());
        assertEquals("User", userDTO.getLastName());
    }


    @Test
    void testSaveUser() {
        UserDTO userDTO = new UserDTO("testuser", "test@example.com", "Test", "User");

        userService.saveUser(userDTO);

        verify(jdbcTemplate).update(anyString(), eq("testuser"), eq("test@example.com"), eq("Test"), eq("User"));
    }


    @Test
    void testFetchUsers() {
        ListUsersResponse response = ListUsersResponse.builder()
                .users(
                        UserType.builder()
                                .username("testuser")
                                .attributes(
                                        AttributeType.builder().name("email").value("test@example.com").build(),
                                        AttributeType.builder().name("given_name").value("Test").build(),
                                        AttributeType.builder().name("family_name").value("User").build()
                                )
                                .build()
                )
                .build();
        when(cognitoClient.listUsers(any(ListUsersRequest.class))).thenReturn(response);

        List<UserDTO> users = userService.fetchUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
        assertEquals("test@example.com", users.get(0).getEmail());
        assertEquals("Test", users.get(0).getFirstName());
        assertEquals("User", users.get(0).getLastName());
    }

    @Test
    void testSyncUsers() {
        ListUsersResponse response = ListUsersResponse.builder()
                .users(
                        UserType.builder()
                                .username("testuser")
                                .attributes(
                                        AttributeType.builder().name("email").value("test@example.com").build(),
                                        AttributeType.builder().name("given_name").value("Test").build(),
                                        AttributeType.builder().name("family_name").value("User").build()
                                )
                                .build()
                )
                .build();
        when(cognitoClient.listUsers(any(ListUsersRequest.class))).thenReturn(response);

        userService.syncUsers();

        verify(jdbcTemplate).update(anyString(), eq("testuser"), eq("test@example.com"), eq("Test"), eq("User"));
    }
}
