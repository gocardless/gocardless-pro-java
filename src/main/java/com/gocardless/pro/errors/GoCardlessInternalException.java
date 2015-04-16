package com.gocardless.pro.errors;

/**
 * Exception thrown when an internal error occurred while processing your request.
 */
public class GoCardlessInternalException extends GoCardlessApiException {
    GoCardlessInternalException(ApiErrorResponse error) {
        super(error);
    }
}
