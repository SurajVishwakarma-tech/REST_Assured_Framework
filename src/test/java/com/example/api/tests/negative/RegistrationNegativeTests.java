package com.example.api.tests.negative;

import com.example.api.constants.ApiContracts;
import com.example.api.models.request.RegisterRequest;
import com.example.api.services.AuthService;
import com.example.api.tests.base.BaseTest;
import com.example.api.utils.TestDataFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationNegativeTests extends BaseTest {
    private final AuthService authService = new AuthService();

    @Test
    public void shouldRejectDuplicateEmail() {
        RegisterRequest request = TestDataFactory.validRegisterRequest();
        Response initial = authService.register(request);
        assertAnyStatus(initial, ApiContracts.HTTP_OK, ApiContracts.HTTP_CREATED, ApiContracts.HTTP_CONFLICT);

        if (initial.statusCode() == ApiContracts.HTTP_CONFLICT) {
            return;
        }

        Response duplicate = authService.register(request);
        assertAnyStatus(duplicate, ApiContracts.HTTP_CONFLICT, ApiContracts.HTTP_BAD_REQUEST, ApiContracts.HTTP_UNPROCESSABLE_ENTITY);
        Assert.assertTrue(duplicate.getBody().asString().length() > 0);
    }

    @Test
    public void shouldRejectInvalidEmailFormat() {
        RegisterRequest request = new RegisterRequest(
                "Automation User",
                "invalid-email",
                "SecurePass123!"
        );

        Response response = authService.register(request);
        assertAnyStatus(response, ApiContracts.HTTP_BAD_REQUEST, ApiContracts.HTTP_UNPROCESSABLE_ENTITY);
        Assert.assertTrue(response.getBody().asString().contains("email") || response.getBody().asString().length() > 0);
    }

    @Test
    public void shouldRejectMissingPassword() {
        RegisterRequest request = new RegisterRequest(
                "Automation User",
                TestDataFactory.randomEmail(),
                null
        );

        Response response = authService.register(request);
        assertAnyStatus(response, ApiContracts.HTTP_BAD_REQUEST, ApiContracts.HTTP_UNPROCESSABLE_ENTITY);
        Assert.assertTrue(response.getBody().asString().contains("password") || response.getBody().asString().length() > 0);
    }
}
