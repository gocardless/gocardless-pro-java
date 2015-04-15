package com.gocardless.pro.exceptions;

/**
 * Execption thrown when the parameters submitted with your request were invalid.
 */
public class ValidationFailedException extends GoCardlessApiException {
    public ValidationFailedException(ApiErrorResponse error) {
        super(error);
    }
}
