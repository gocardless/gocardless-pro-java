package com.gocardless.pro.exceptions;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;

public class ApiErrorResponse {
    private final String message;
    private final ErrorType type;
    private final String documentationUrl;
    private final String requestId;
    private final int code;
    private final List<ApiError> errors;

    public ApiErrorResponse(String message, ErrorType type, String documentationUrl,
            String requestId, int code, List<ApiError> errors) {
        this.message = message;
        this.type = type;
        this.documentationUrl = documentationUrl;
        this.requestId = requestId;
        this.code = code;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public ErrorType getType() {
        return type;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public String getRequestId() {
        return requestId;
    }

    public int getCode() {
        return code;
    }

    public List<ApiError> getErrors() {
        if (errors == null) {
            return ImmutableList.of();
        }
        return ImmutableList.copyOf(errors);
    }

    public enum ErrorType {
        @SerializedName("gocardless")
        GOCARDLESS, @SerializedName("invalid_api_usage")
        INVALID_API_USAGE, @SerializedName("invalid_state")
        INVALID_STATE, @SerializedName("validation_failed")
        VALIDATION_FAILED
    }

    public static class ApiError {
        private final String message;
        private final String reason;
        private final String field;
        private final Map<String, String> links;

        public ApiError(String message, String reason, String field, Map<String, String> links) {
            this.message = message;
            this.reason = reason;
            this.field = field;
            this.links = links;
        }

        public String getMessage() {
            return message;
        }

        public String getReason() {
            return reason;
        }

        public String getField() {
            return field;
        }

        public Map<String, String> getLinks() {
            if (links == null) {
                return ImmutableMap.of();
            }
            return ImmutableMap.copyOf(links);
        }
    }
}
