package com.example.api.services;

import com.example.api.auth.TokenContext;
import com.example.api.clients.ApiClient;
import com.example.api.constants.ApiEndpoints;
import com.example.api.constants.ApiContracts;
import com.example.api.models.request.LoginRequest;
import com.example.api.models.request.RegisterRequest;
import com.example.api.models.response.AuthResponse;
import io.restassured.response.Response;

public class AuthService {
    private final ApiClient apiClient;

    public AuthService() {
        this.apiClient = new ApiClient();
    }

    public Response register(RegisterRequest request) {
        return apiClient.post(ApiEndpoints.register(), request);
    }

    public AuthResponse registerAndReturn(RegisterRequest request) {
        Response response = register(request);
        return response.as(AuthResponse.class);
    }

    public Response login(LoginRequest request) {
        return apiClient.post(ApiEndpoints.login(), request);
    }

    public AuthResponse loginAndReturn(LoginRequest request) {
        Response response = login(request);
        String token = response.jsonPath().getString("accessToken");
        if (token != null && !token.isBlank()) {
            TokenContext.set(token);
        }
        return response.as(AuthResponse.class);
    }

    public Response logout() {
        return apiClient.post(ApiEndpoints.logout(), null);
    }

    public Response getCurrentUser() {
        return apiClient.authenticatedRequest().when().get(ApiEndpoints.me());
    }

    public void clearToken() {
        TokenContext.clear();
    }
}
