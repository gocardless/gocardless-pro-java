package com.gocardless.errors;

/**
 * Exception thrown when user credentials/api key provided is invalid.
 */
public class AuthenticationException extends InvalidApiUsageException {
    AuthenticationException(ApiErrorResponse error) {
        super(error);
    }
}
