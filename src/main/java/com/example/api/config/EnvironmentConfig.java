package com.example.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class EnvironmentConfig {
    private static final Properties PROPERTIES = loadProperties();

    private EnvironmentConfig() {
    }

    public static String getBaseUri() {
        return resolveValue("api.base.uri", "http://localhost:8080");
    }

    public static String getBasePath() {
        return resolveValue("api.base.path", "/api");
    }

    public static String getAuthRegisterEndpoint() {
        return resolveValue("auth.register.endpoint", "/auth/register");
    }

    public static String getAuthLoginEndpoint() {
        return resolveValue("auth.login.endpoint", "/auth/login");
    }

    public static String getAuthLogoutEndpoint() {
        return resolveValue("auth.logout.endpoint", "/auth/logout");
    }

    public static String getAuthMeEndpoint() {
        return resolveValue("auth.me.endpoint", "/auth/me");
    }

    public static int getTimeoutMs() {
        return Integer.parseInt(resolveValue("request.timeout.ms", "30000"));
    }

    public static boolean isApiConfigured() {
        return !getBaseUri().isBlank() && !getBaseUri().equals("http://localhost:8080");
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = EnvironmentConfig.class.getClassLoader()
                .getResourceAsStream("config/application.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load application.properties", e);
        }
        return properties;
    }

    private static String resolveValue(String key, String defaultValue) {
        String envKey = key.toUpperCase().replace('.', '_').replace('-', '_');
        String value = System.getenv(envKey);
        if (value != null && !value.isBlank()) {
            return value;
        }

        value = System.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value;
        }

        value = PROPERTIES.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value;
        }

        return defaultValue;
    }
}
