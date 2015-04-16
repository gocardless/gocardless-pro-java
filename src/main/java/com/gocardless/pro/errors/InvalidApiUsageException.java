package com.gocardless.pro.errors;

/**
 * Exception thrown when there is an error with the request you made.
 */
public class InvalidApiUsageException extends GoCardlessApiException {
    InvalidApiUsageException(ApiErrorResponse error) {
        super(error);
    }
}
