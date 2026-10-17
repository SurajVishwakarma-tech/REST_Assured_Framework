package com.example.api.specifications;

import com.example.api.clients.ApiClient;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecs {
    private RequestSpecs() {
    }

    public static RequestSpecification basicRequest() {
        return new ApiClient().defaultRequest();
    }

    public static RequestSpecification authenticatedRequest() {
        return new ApiClient().authenticatedRequest();
    }
}
