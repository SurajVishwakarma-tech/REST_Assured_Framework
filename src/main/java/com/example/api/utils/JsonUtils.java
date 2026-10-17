package com.example.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private JsonUtils() {
    }

    public static String toJson(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to serialize payload to JSON", e);
        }
    }

    public static <T> T fromJson(String body, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(body, type);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to deserialize JSON payload", e);
        }
    }
}
