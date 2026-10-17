package com.example.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private String message;
    private String error;
    private Map<String, Object> validationErrors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, Object> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, Object> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
