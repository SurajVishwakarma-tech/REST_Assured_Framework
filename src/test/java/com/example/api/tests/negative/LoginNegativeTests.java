package com.example.api.tests.negative;

import com.example.api.constants.ApiContracts;
import com.example.api.models.request.LoginRequest;
import com.example.api.services.AuthService;
import com.example.api.tests.base.BaseTest;
import com.example.api.utils.TestDataFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginNegativeTests extends BaseTest {
    private final AuthService authService = new AuthService();

    @Test
    public void shouldRejectIncorrectPassword() {
        LoginRequest request = new LoginRequest("automation@example.com", "WrongPassword123!");
        Response response = authService.login(request);
        assertAnyStatus(response, ApiContracts.HTTP_BAD_REQUEST, ApiContracts.HTTP_UNAUTHORIZED, ApiContracts.HTTP_NOT_FOUND);
        Assert.assertTrue(response.getBody().asString().length() > 0);
    }

    @Test
    public void shouldRejectUnregisteredEmail() {
        LoginRequest request = new LoginRequest(TestDataFactory.randomEmail(), "SecurePass123!");
        Response response = authService.login(request);
        assertAnyStatus(response, ApiContracts.HTTP_BAD_REQUEST, ApiContracts.HTTP_UNAUTHORIZED, ApiContracts.HTTP_NOT_FOUND);
        Assert.assertTrue(response.getBody().asString().length() > 0);
    }

    @Test
    public void shouldRejectMissingEmail() {
        LoginRequest request = new LoginRequest(null, "SecurePass123!");
        Response response = authService.login(request);
        assertAnyStatus(response, ApiContracts.HTTP_BAD_REQUEST, ApiContracts.HTTP_UNPROCESSABLE_ENTITY);
        Assert.assertTrue(response.getBody().asString().contains("email") || response.getBody().asString().length() > 0);
    }
}
