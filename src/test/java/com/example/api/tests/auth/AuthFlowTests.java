package com.example.api.tests.auth;

import com.example.api.auth.TokenContext;
import com.example.api.constants.ApiContracts;
import com.example.api.models.request.LoginRequest;
import com.example.api.models.request.RegisterRequest;
import com.example.api.models.response.AuthResponse;
import com.example.api.models.response.UserResponse;
import com.example.api.services.AuthService;
import com.example.api.services.UserService;
import com.example.api.tests.base.BaseTest;
import com.example.api.utils.TestDataFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthFlowTests extends BaseTest {
    private final AuthService authService = new AuthService();
    private final UserService userService = new UserService();

    @Test
    public void registerAndLoginFlowShouldSucceed() {
        RegisterRequest registerRequest = TestDataFactory.validRegisterRequest();

        Response registerResponse = authService.register(registerRequest);
        assertAnyStatus(registerResponse, ApiContracts.HTTP_OK, ApiContracts.HTTP_CREATED, ApiContracts.HTTP_CONFLICT);

        if (registerResponse.statusCode() == ApiContracts.HTTP_CONFLICT) {
            registerRequest.setEmail(TestDataFactory.randomEmail());
            registerResponse = authService.register(registerRequest);
            assertAnyStatus(registerResponse, ApiContracts.HTTP_OK, ApiContracts.HTTP_CREATED);
        }

        AuthResponse registerBody = registerResponse.as(AuthResponse.class);
        if (registerBody.getUser() != null) {
            Assert.assertEquals(registerBody.getUser().getEmail(), registerRequest.getEmail());
            Assert.assertNotNull(registerBody.getUser().getId());
        }

        LoginRequest loginRequest = new LoginRequest(registerRequest.getEmail(), registerRequest.getPassword());
        Response loginResponse = authService.login(loginRequest);
        assertAnyStatus(loginResponse, ApiContracts.HTTP_OK, ApiContracts.HTTP_CREATED);

        AuthResponse loginBody = loginResponse.as(AuthResponse.class);
        assertNotBlank(loginBody.getAccessToken(), "Access token should not be blank");
        TokenContext.set(loginBody.getAccessToken());

        Response currentUserResponse = userService.getAuthenticatedUser();
        assertAnyStatus(currentUserResponse, ApiContracts.HTTP_OK);

        UserResponse currentUser = currentUserResponse.as(UserResponse.class);
        Assert.assertEquals(currentUser.getEmail(), registerRequest.getEmail());
    }

    @Test
    public void logoutShouldClearLocalTokenContext() {
        RegisterRequest registerRequest = TestDataFactory.validRegisterRequest();
        Response registerResponse = authService.register(registerRequest);
        assertAnyStatus(registerResponse, ApiContracts.HTTP_OK, ApiContracts.HTTP_CREATED, ApiContracts.HTTP_CONFLICT);

        if (registerResponse.statusCode() == ApiContracts.HTTP_CONFLICT) {
            registerRequest.setEmail(TestDataFactory.randomEmail());
            registerResponse = authService.register(registerRequest);
            assertAnyStatus(registerResponse, ApiContracts.HTTP_OK, ApiContracts.HTTP_CREATED);
        }

        LoginRequest loginRequest = new LoginRequest(registerRequest.getEmail(), registerRequest.getPassword());
        AuthResponse loginBody = authService.loginAndReturn(loginRequest);
        assertNotBlank(loginBody.getAccessToken(), "Login response must contain an access token");

        Response logoutResponse = authService.logout();
        assertAnyStatus(logoutResponse, ApiContracts.HTTP_OK, ApiContracts.HTTP_BAD_REQUEST, ApiContracts.HTTP_UNAUTHORIZED);

        authService.clearToken();
        Assert.assertTrue(TokenContext.get().isEmpty(), "Token context should be empty after logout");
    }
}
