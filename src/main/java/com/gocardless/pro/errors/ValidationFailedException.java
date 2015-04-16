package com.gocardless.pro.errors;

/**
 * Exception thrown when the parameters submitted with your request were invalid.
 */
public class ValidationFailedException extends GoCardlessApiException {
    ValidationFailedException(ApiErrorResponse error) {
        super(error);
    }
}
