package com.gocardless.errors;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Representation of an individual error handling an API request.
 */
public class ApiError {
    private final String message;
    private final String reason;
    private final String field;
    private final Map<String, String> links;

    private ApiError(String message, String reason, String field, Map<String, String> links) {
        this.message = message;
        this.reason = reason;
        this.field = field;
        this.links = links;
    }

    /**
     * Returns a message describing the problem.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns a key identifying the reason for this error.
     */
    public String getReason() {
        return reason;
    }

    /**
     * Returns the field that this error applies to.
     */
    public String getField() {
        return field;
    }

    /**
     * Returns the IDs of related objects.
     */
    public Map<String, String> getLinks() {
        if (links == null) {
            return ImmutableMap.of();
        }
        return ImmutableMap.copyOf(links);
    }
}
