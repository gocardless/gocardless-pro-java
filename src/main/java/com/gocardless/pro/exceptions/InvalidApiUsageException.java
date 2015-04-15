package com.gocardless.pro.exceptions;

/**
 * Exception thrown when there is an error with the request you made.
 */
public class InvalidApiUsageException extends GoCardlessApiException {
    public InvalidApiUsageException(ApiErrorResponse error) {
        super(error);
    }
}
