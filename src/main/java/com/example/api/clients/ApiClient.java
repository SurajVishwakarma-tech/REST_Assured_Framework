package com.example.api.clients;

import com.example.api.auth.TokenContext;
import com.example.api.config.EnvironmentConfig;
import com.example.api.constants.ApiContracts;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;

public class ApiClient {
    private final String baseUri;
    private final String basePath;

    public ApiClient() {
        this.baseUri = EnvironmentConfig.getBaseUri();
        this.basePath = EnvironmentConfig.getBasePath();
    }

    public RequestSpecification defaultRequest() {
        return RestAssured.given()
                .baseUri(baseUri)
                .basePath(basePath)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

    public RequestSpecification authenticatedRequest() {
        return defaultRequest().header(HttpHeaders.AUTHORIZATION,
                ApiContracts.AUTH_HEADER_PREFIX + TokenContext.getOrThrow());
    }

    public Response get(String path) {
        return defaultRequest().when().get(path);
    }

    public Response post(String path, Object body) {
        return defaultRequest().body(body).when().post(path);
    }

    public Response put(String path, Object body) {
        return defaultRequest().body(body).when().put(path);
    }

    public Response patch(String path, Object body) {
        return defaultRequest().body(body).when().patch(path);
    }

    public Response delete(String path) {
        return defaultRequest().when().delete(path);
    }
}
