package com.example.api.auth;

import java.util.Optional;

public final class TokenContext {
    private static final ThreadLocal<String> ACCESS_TOKEN = new ThreadLocal<>();

    private TokenContext() {
    }

    public static void set(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            ACCESS_TOKEN.remove();
            return;
        }
        ACCESS_TOKEN.set(accessToken);
    }

    public static Optional<String> get() {
        return Optional.ofNullable(ACCESS_TOKEN.get());
    }

    public static String getOrThrow() {
        return get().orElseThrow(() -> new IllegalStateException("No JWT token available for the current test context."));
    }

    public static void clear() {
        ACCESS_TOKEN.remove();
    }
}
