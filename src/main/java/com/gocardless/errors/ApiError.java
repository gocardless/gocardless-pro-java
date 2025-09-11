package com.gocardless.errors;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Representation of an individual error handling an API request.
 */
public class ApiError {
    private static final Joiner JOINER = Joiner.on(" ").skipNulls();

    private final String message;
    private final String reason;
    private final String field;
    private final String requestPointer;
    private final Map<String, String> links;

    private ApiError(String message, String reason, String field, String requestPointer, Map<String, String> links) {
        this.message = message;
        this.reason = reason;
        this.field = field;
        this.requestPointer = requestPointer;
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
     * Returns the request pointer, indicating the exact field of the request that
     * triggered the validation error
     */
    public String getRequestPointer() {
        return requestPointer;
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

    @Override
    public String toString() {
        return JOINER.join(field, message);
    }
}
