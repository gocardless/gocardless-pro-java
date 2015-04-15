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

    private ApiErrorResponse(String message, ErrorType type, String documentationUrl,
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

    /**
     * Types of error that can be returned by the API.
     */
    public enum ErrorType {
        /**
         * An internal error occurred while processing your request. This should be
         * reported to our support team with the id, so we can resolve the issue.
         */
        @SerializedName("gocardless")
        GOCARDLESS,
        /**
         * This is an error with the request you made. It could be an invalid URL, the
         * authentication header could be missing, invalid, or grant insufficient
         * permissions, you may have reached your rate limit, or the syntax of your
         * request could be incorrect. The errors will give more detail of the specific
         * issue.
         */
        @SerializedName("invalid_api_usage")
        INVALID_API_USAGE,
        /**
         * The action you are trying to perform is invalid due to the state of the
         * resource you are requesting it on. For example, a payment you are trying to
         * cancel might already have been submitted. The errors will give more details.
         */
        @SerializedName("invalid_state")
        INVALID_STATE,
        /**
         * The parameters submitted with your request were invalid. Details of which
         * fields were invalid and why are included in the response.
         */
        @SerializedName("validation_failed")
        VALIDATION_FAILED
    }

    /**
     * Representation of an error handling an API request.
     */
    public static class ApiError {
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
}
