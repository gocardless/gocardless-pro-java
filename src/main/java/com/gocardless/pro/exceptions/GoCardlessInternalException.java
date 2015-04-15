package com.gocardless.pro.exceptions;

/**
 * Exception thrown when an internal error occurred while processing your request.
 */
public class GoCardlessInternalException extends GoCardlessApiException {
    public GoCardlessInternalException(ApiErrorResponse error) {
        super(error);
    }
}
