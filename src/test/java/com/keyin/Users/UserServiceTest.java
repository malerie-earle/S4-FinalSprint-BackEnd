package com.keyin.Users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private CognitoIdentityProviderClient cognitoClient;

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

        userService.signUp("testUser", "testPass", "test@example.com");
    }

    @Test
    void testSignUpFailure() {
        when(cognitoClient.adminCreateUser(any(AdminCreateUserRequest.class)))
                .thenThrow(CognitoIdentityProviderException.builder().message("Error").build());

        assertThrows(RuntimeException.class, () -> {
            userService.signUp("testUser", "testPass", "test@example.com");
        });
    }

    @Test
    void testSignInSuccess() {
        AdminInitiateAuthResponse response = AdminInitiateAuthResponse.builder()
                .authenticationResult(AuthenticationResultType.builder().idToken("idToken").accessToken("accessToken").refreshToken("refreshToken").build())
                .build();
        when(cognitoClient.adminInitiateAuth(any(AdminInitiateAuthRequest.class))).thenReturn(response);

        userService.signIn("testUser", "testPass");
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
}
