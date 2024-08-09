/*package com.keyin.Users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testSignUp() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .param("username", "testUser")
                        .param("password", "testPass")
                        .param("email", "test@example.com")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void testSignIn() throws Exception {
        AdminInitiateAuthResponse response = AdminInitiateAuthResponse.builder()
                .authenticationResult(AuthenticationResultType.builder().idToken("idToken").accessToken("accessToken").refreshToken("refreshToken").build())
                .build();
        when(userService.signIn(any(String.class), any(String.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/signin")
                        .param("username", "testUser")
                        .param("password", "testPass")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"idToken\":\"idToken\",\"accessToken\":\"accessToken\",\"refreshToken\":\"refreshToken\"}"));
    }

    @Test
    void testResetPassword() throws Exception {
        mockMvc.perform(post("/api/auth/reset-password")
                        .param("username", "testUser")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string("Password reset successfully"));
    }
}*/
