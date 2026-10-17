package com.example.api.specifications;

import com.example.api.constants.ApiContracts;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public final class ResponseSpecs {
    private ResponseSpecs() {
    }

    public static ResponseSpecification success() {
        return new ResponseSpecBuilder()
                .expectStatusCode(ApiContracts.HTTP_OK)
                .build();
    }

    public static ResponseSpecification created() {
        return new ResponseSpecBuilder()
                .expectStatusCode(ApiContracts.HTTP_CREATED)
                .build();
    }

    public static ResponseSpecification clientError() {
        return new ResponseSpecBuilder()
                .expectStatusCode(ApiContracts.HTTP_BAD_REQUEST)
                .build();
    }

    public static ResponseSpecification unauthorized() {
        return new ResponseSpecBuilder()
                .expectStatusCode(ApiContracts.HTTP_UNAUTHORIZED)
                .build();
    }
}
