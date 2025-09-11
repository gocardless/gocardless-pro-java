package com.gocardless.errors;

import com.gocardless.GoCardlessException;

import java.util.List;

/**
 * Base class for exceptions that are thrown as a result of error responses from
 * the API.
 */
public class GoCardlessApiException extends GoCardlessException {
    private final ApiErrorResponse error;

    GoCardlessApiException(ApiErrorResponse error) {
        super(error.toString());

        this.error = error;
    }

    /**
     * Returns a human-readable description of the error.
     */
    public String getErrorMessage() {
        return error.getMessage();
    }

    /**
     * Returns the type of the error.
     */
    public ErrorType getType() {
        return error.getType();
    }

    /**
     * Returns the URL to the documentation describing the error.
     */
    public String getDocumentationUrl() {
        return error.getDocumentationUrl();
    }

    /**
     * Returns the ID of the request.  This can be used to help the support
     * team find your error quickly.
     */
    public String getRequestId() {
        return error.getRequestId();
    }

    /**
     * Returns the HTTP status code.
     */
    public int getCode() {
        return error.getCode();
    }

    /**
     * Returns a list of errors.
     */
    public List<ApiError> getErrors() {
        return error.getErrors();
    }

    @Override
    public String toString() {
        return error.toString();
    }
}
