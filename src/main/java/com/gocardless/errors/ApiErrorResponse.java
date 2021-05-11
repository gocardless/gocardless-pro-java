package com.gocardless.errors;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

public class ApiErrorResponse {
    private static final Joiner JOINER = Joiner.on(", ");

    private final String message;
    private final ErrorType type;
    private final String documentationUrl;
    private final String requestId;
    private final int code;
    private final List<ApiError> errors;

    private ApiErrorResponse(String message,
                             ErrorType type,
                             String documentationUrl,
                             String requestId,
                             int code,
                             List<ApiError> errors) {
        this.message = message;
        this.type = type;
        this.documentationUrl = documentationUrl;
        this.requestId = requestId;
        this.code = code;
        this.errors = errors;
    }

    String getMessage() {
        return message;
    }

    ErrorType getType() {
        return type;
    }

    String getDocumentationUrl() {
        return documentationUrl;
    }

    String getRequestId() {
        return requestId;
    }

    int getCode() {
        return code;
    }

    List<ApiError> getErrors() {
        if (errors == null) {
            return ImmutableList.of();
        }

        return ImmutableList.copyOf(errors);
    }

    @Override
    public String toString() {
        if (errors == null || errors.isEmpty()) {
            return message;
        } else {
            return JOINER.join(errors);
        }
    }
}
