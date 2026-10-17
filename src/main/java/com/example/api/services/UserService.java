package com.example.api.services;

import com.example.api.auth.TokenContext;
import com.example.api.clients.ApiClient;
import com.example.api.constants.ApiEndpoints;
import com.example.api.models.response.UserResponse;
import io.restassured.response.Response;

public class UserService {
    private final ApiClient apiClient;

    public UserService() {
        this.apiClient = new ApiClient();
    }

    public Response getAuthenticatedUser() {
        return apiClient.authenticatedRequest().when().get(ApiEndpoints.me());
    }

    public UserResponse getAuthenticatedUserAsModel() {
        Response response = getAuthenticatedUser();
        return response.as(UserResponse.class);
    }

    public String getCurrentToken() {
        return TokenContext.getOrThrow();
    }
}
