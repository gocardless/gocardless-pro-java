package com.gocardless.pro.exceptions;

public class InvalidApiUsageException extends GoCardlessApiException {
    public InvalidApiUsageException(ApiErrorResponse error) {
        super(error);
    }
}
