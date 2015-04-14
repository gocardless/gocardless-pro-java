package com.gocardless.pro.exceptions;

public class ValidationFailedException extends GoCardlessApiException {
    public ValidationFailedException(ApiErrorResponse error) {
        super(error);
    }
}
