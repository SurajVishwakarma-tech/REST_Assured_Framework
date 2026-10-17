package com.example.api.constants;

import com.example.api.config.EnvironmentConfig;

public final class ApiEndpoints {
    private ApiEndpoints() {
    }

    public static String register() {
        return EnvironmentConfig.getBasePath() + EnvironmentConfig.getAuthRegisterEndpoint();
    }

    public static String login() {
        return EnvironmentConfig.getBasePath() + EnvironmentConfig.getAuthLoginEndpoint();
    }

    public static String logout() {
        return EnvironmentConfig.getBasePath() + EnvironmentConfig.getAuthLogoutEndpoint();
    }

    public static String me() {
        return EnvironmentConfig.getBasePath() + EnvironmentConfig.getAuthMeEndpoint();
    }
}
