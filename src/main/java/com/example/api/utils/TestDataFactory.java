package com.example.api.utils;

import com.example.api.models.request.LoginRequest;
import com.example.api.models.request.RegisterRequest;

import java.util.UUID;

public final class TestDataFactory {
    private TestDataFactory() {
    }

    public static RegisterRequest validRegisterRequest() {
        String unique = UUID.randomUUID().toString().substring(0, 8);
        return new RegisterRequest(
                "Automation User " + unique,
                "automation." + unique + "@example.com",
                "SecurePass123!"
        );
    }

    public static LoginRequest validLoginRequest(String email, String password) {
        return new LoginRequest(email, password);
    }

    public static String randomEmail() {
        return "qa." + UUID.randomUUID() + "@example.com";
    }
}
